<template>
    <div>
        <b-modal id="modal-reassign-doctor" title="Asignar doctor" centered hide-footer scrollable
            :no-close-on-backdrop="true" @hidden="cleanForm" @close="cleanForm">
            <b-form>
                <label>Selecciona el doctor:
                    <span class="text-danger">*</span>
                </label>
                <b-form-select v-model="v$.selectedDoctor.$model" :options="doctorOptions"
                    :state="v$.selectedDoctor.$dirty ? !v$.selectedDoctor.$error : null"
                    @touch="v$.selectedDoctor.$touch()" @change="getHours">
                    <template #first>
                        <b-form-select-option :value="null">
                            Selecciona un doctor
                        </b-form-select-option>
                    </template>
                </b-form-select>
                <b-form-invalid-feedback v-if="!v$.selectedDoctor.required.$response">{{ errorMessages.required
                    }}</b-form-invalid-feedback>

                <label class="mt-2">Selecciona la hora programada:
                    <span class="text-danger">*</span>
                </label>
                <b-form-select v-model="v$.selectedHour.$model" :options="hoursOptions"
                    :state="v$.selectedHour.$dirty ? !v$.selectedHour.$error : null" @touch="v$.selectedHour.$touch()">
                    <template #first>
                        <b-form-select-option :value="null">
                            Selecciona una hora
                        </b-form-select-option>
                    </template>
                </b-form-select>
                <b-form-invalid-feedback v-if="!v$.selectedHour.required.$response">{{ errorMessages.required
                    }}</b-form-invalid-feedback>

                <div class="col-12 mt-4 px-5 d-flex justify-content-between">
                    <b-button variant="danger" @click="closeModal">Cancelar</b-button>
                    <b-button variant="success" class="ml-2"
                        :disabled="v$.selectedDoctor.$invalid || v$.selectedHour.$invalid"
                        @click="assignDoctor">Asignar</b-button>
                </div>
            </b-form>
        </b-modal>
    </div>
</template>

<script>
import Vue from 'vue';
import { useVuelidate } from "@vuelidate/core";
import { required, helpers } from "@vuelidate/validators";
import { encrypt } from '../../../../../kernel/hashFunctions';
import appointmentsController from '../../services/controller/appointments.controller';
import doctorController from '../../../doctors/services/controller/doctor.controller';
import SweetAlertCustom from '../../../../../kernel/SweetAlertCustom';

export default Vue.extend({
    name: "ReassignDoctor",
    setup() {
        return { v$: useVuelidate() }
    },
    props: {
        appointmentSelected: {
            required: true,
            type: Object,
        }
    },
    data() {
        return {
            hoursOptions: [],
            doctorOptions: [],
            selectedDoctor: null,
            selectedHour: null,
            appointment: {},
            errorMessages: {
                required: "Campo obligatorio"
            }
        }
    },
    watch: {
        appointmentSelected() {
            this.appointment = {
                ...this.appointmentSelected
            };
            this.getAvailableDoctors()
        },
    },
    methods: {
        async getAvailableDoctors() {
            try {
                const resp = await doctorController.getAllAvailableAuxDoctors({
                    scheduledAt: this.appointment.scheduledAt,
                    speciality: this.appointment.speciality,
                    shift: this.appointment.preferentialShift
                });
                this.doctorOptions = resp.map((doctor) => ({
                    value: doctor.id,
                    text: doctor.fullName
                }))
            } catch (error) {
                console.error("Error al obtener a los doctores disponibles: ", error);
            }
        },
        async getHours() {
            this.selectedHour = null;
            try {
                if (this.selectedDoctor) {
                    const resp = await doctorController.getAvailableHours({
                        scheduledAt: this.appointment.scheduledAt,
                        id: this.selectedDoctor
                    });
                    this.hoursOptions = resp;
                } else {
                    this.hoursOptions = [];
                }
            } catch (error) {
                console.error("Error al obtener las horas disponibles: ", error);
            }
        },
        async assignDoctor() {
            try {
                if (this.v$.selectedDoctor.$invalid || this.v$.selectedHour.$invalid) {
                    SweetAlertCustom.invalidForm();
                } else {
                    const result = await SweetAlertCustom.questionMessage();
                    if (result.isConfirmed) {
                        this.$emit("showLoading");
                        const cipherId = await encrypt(this.appointment.id);
                        const resp = await appointmentsController.reassignDoctor(cipherId, {
                            doctor: {
                                id: this.selectedDoctor
                            },
                            scheduledHour: this.selectedHour
                        });
                        const { error } = resp;
                        if (!error) {
                            this.$emit("reloadRegisters");
                            setTimeout(() => {
                                SweetAlertCustom.successMessage();
                            }, 100);
                            this.$nextTick(() =>
                                this.$bvModal.hide("modal-reassign-doctor")
                            );
                            this.cleanForm();
                            return;
                        }
                    }
                }
            } catch (error) {
                console.log(error);
            } finally{
                this.$emit("hideLoading");
            }
        },
        cleanForm() {
            this.selectedDoctor = null;
            this.selectedHour = null;
            this.v$.selectedDoctor.$reset();
            this.v$.selectedHour.$reset();
        },
        closeModal() {
            this.$bvModal.hide("modal-reassign-doctor");
        },
    },
    validations() {
        return {
            selectedDoctor: {
                required: helpers.withMessage(this.errorMessages.required, required),
            },
            selectedHour: {
                required: helpers.withMessage(this.errorMessages.required, required),
            }
        }
    },
})
</script>
