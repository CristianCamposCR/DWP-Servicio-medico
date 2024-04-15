<template>
    <div>
        <b-modal id="modal-reeschedule-appointment" title="Reagendar cita" centered hide-footer scrollable
            :no-close-on-backdrop="true" @hidden="cleanForm" @close="cleanForm">
            <b-form>
                <label>Selecciona la fecha:
                    <span class="text-danger">*</span>
                </label>
                <b-calendar block v-model="newScheduledAt" label-no-date-selected="Selecciona una fecha"
                    label-selected="Fecha seleccionada" selected-variant="primary"
                    :initial-date="new Date(dateTomorrow())" :min="new Date(dateTomorrow())"
                    :max="new Date(dateMoreYear(2))" today-variant="info" nav-button-variant="primary"
                    label-help="Usa las flechas simples para navegar entre los meses y las dobles para cambiar de año"></b-calendar>

                <label class="mt-2">Selecciona el turno:
                    <span class="text-danger">*</span>
                </label>
                <b-form-select v-model="v$.selectedNewShift.$model" :options="shiftOptions"
                    :state="v$.selectedNewShift.$dirty ? !v$.selectedNewShift.$error : null"
                    @touch="v$.selectedNewShift.$touch()">
                    <template #first>
                        <b-form-select-option :value="null">
                            Selecciona un turno
                        </b-form-select-option>
                    </template>
                </b-form-select>
                <b-form-invalid-feedback v-if="!v$.selectedNewShift.required.$response">{{ errorMessages.required
                    }}</b-form-invalid-feedback>

                <div class="col-12 mt-4 px-5 d-flex justify-content-between">
                    <b-button variant="danger" @click="closeModal">Cancelar</b-button>
                    <b-button variant="success" class="ml-2"
                        :disabled="v$.newScheduledAt.$invalid || v$.selectedNewShift.$invalid"
                        @click="reescheduleAppointment">Reagendar</b-button>
                </div>
            </b-form>
        </b-modal>
    </div>
</template>

<script>
import Vue from 'vue';
import moment from 'moment';
import { useVuelidate } from "@vuelidate/core";
import { required, helpers } from "@vuelidate/validators";
import { encrypt } from '../../../../../kernel/hashFunctions';
import appointmentController from '../../services/controller/appointment.controller';
import shiftController from '../../../../public/shift/services/controller/shift.controller';
import SweetAlertCustom from '../../../../../kernel/SweetAlertCustom';

export default Vue.extend({
    name: "Reeschedule",
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
            shiftOptions: [],
            selectedNewShift: null,
            newScheduledAt: null,
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
        },
    },
    methods: {
        dateTomorrow() {
            const today = moment();
            today.add(1, 'days').format('YYYY-MM-DD');
            return today;
        },
        dateMoreYear(year) {
            const today = moment();
            today.add(year, 'years').format('YYYY-MM-DD');
            return today;
        },
        async getAllShift() {
            try {
                const response = await shiftController.getAllShift();
                this.shiftOptions = response.map((shift) => ({
                    value: shift.id,
                    text: shift.name
                }))
            } catch (error) {
                console.error(error);
            }
        },
        async reescheduleAppointment() {
            try {
                if (this.v$.selectedNewShift.$invalid || this.v$.newScheduledAt.$invalid) {
                    SweetAlertCustom.invalidForm();
                } else {
                    const result = await SweetAlertCustom.questionMessage();
                    if (result.isConfirmed) {
                        this.$emit("showLoading");
                        const cipherId = await encrypt(this.appointment.id);
                        const resp = await appointmentController.reescheduleAppointment(cipherId, {
                            shift: {
                                id: this.selectedNewShift
                            },
                            scheduledAt: this.newScheduledAt
                        });
                        const { error } = resp;
                        if (!error) {
                            this.$emit("reloadRegisters");
                            setTimeout(() => {
                                SweetAlertCustom.successMessage();
                            }, 100);
                            this.$nextTick(() =>
                                this.$bvModal.hide("modal-reeschedule-appointment")
                            );
                            this.cleanForm();
                            return;
                        }
                    }
                }
            } catch (error) {
                console.log(error);
            } finally {
                this.$emit("hideLoading");
            }
        },
        cleanForm() {
            this.selectedNewShift = null;
            this.newScheduledAt = null;
            this.v$.selectedNewShift.$reset();
            this.v$.newScheduledAt.$reset();
        },
        closeModal() {
            this.$bvModal.hide("modal-reeschedule-appointment");
        },
    },
    validations() {
        return {
            selectedNewShift: {
                required: helpers.withMessage(this.errorMessages.required, required),
            },
            newScheduledAt: {
                newScheduledAt: helpers.withMessage(this.errorMessages.required, required),
            }
        }
    },
    mounted() {
        this.getAllShift();
    }
})
</script>
