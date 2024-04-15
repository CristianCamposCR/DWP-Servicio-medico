<template>
  <b-modal
    id="save-records"
    title="Registrar diagnostico"
    :no-close-on-backdrop="true"
    centered
    hide-footer
    @hidden="onClose"
    @close="onClose"
  >
    <div>
      <b-form-group label="Diagnóstico">
        <b-form-textarea
          id="diagnosis"
          placeholder="El paciente tiene..."
          required
          v-model="v$.recordData.diagnosis.$model"
          trim
          :state="
            v$.recordData.diagnosis.$dirty
              ? !v$.recordData.diagnosis.$error
              : null
          "
          @blur="v$.recordData.diagnosis.$touch()"
        ></b-form-textarea>
        <b-form-invalid-feedback
          v-for="error in v$.recordData.diagnosis.$errors"
          :key="error.$uid"
        >
          {{ error.$message }}
        </b-form-invalid-feedback>
      </b-form-group>
      <b-form-group label="Notas del Tratamiento">
        <b-form-textarea
          id="diagnosis"
          placeholder="El paciente debe..."
          required
          v-model="v$.recordData.treatmentNotes.$model"
          trim
          :state="
            v$.recordData.treatmentNotes.$dirty
              ? !v$.recordData.treatmentNotes.$error
              : null
          "
          @blur="v$.recordData.treatmentNotes.$touch()"
        ></b-form-textarea>
        <b-form-invalid-feedback
          v-for="error in v$.recordData.treatmentNotes.$errors"
          :key="error.$uid"
        >
          {{ error.$message }}
        </b-form-invalid-feedback>
      </b-form-group>
      <b-form-group label="Medicamentos">
        <b-form-textarea 
          id="diagnosis"
          placeholder="80 miligramos de..."
          required
          v-model="v$.recordData.medications.$model"
          trim
          :state="
            v$.recordData.medications.$dirty
              ? !v$.recordData.medications.$error
              : null
          "
          @blur="v$.recordData.medications.$touch()"
        ></b-form-textarea>
        <b-form-invalid-feedback
          v-for="error in v$.recordData.medications.$errors"
          :key="error.$uid"
        >
          {{ error.$message }}
        </b-form-invalid-feedback>
      </b-form-group>
    </div>
    <div class="row mt-4 px-md-5">
      <div class="col-md-6 mb-3 mb-md-0">
        <b-button variant="danger" @click="onClose" block>Cancelar</b-button>
      </div>
      <div class="col-md-6">
        <b-button
          variant="success"
          :disabled="v$.recordData.$invalid"
          @click="saveRecords"
          block
        >
          Registrar
        </b-button>
      </div>
    </div>
  </b-modal>
</template>

<script>
import recordsController from "../../services/controller/records.controller";
import SweetAlertCustom from "../../../../../kernel/SweetAlertCustom";
import { encrypt } from "../../../../../kernel/hashFunctions";
import { useVuelidate } from "@vuelidate/core";
import { required, helpers, minLength } from "@vuelidate/validators";
export default {
  setup() {
    return { v$: useVuelidate() };
  },
  props: {
    appointmentSelected: {
      required: true,
      type: Number,
    },
    value: Boolean,
  },
  data() {
    return {
      showModal: false,
      recordData: {
        diagnosis: "",
        treatmentNotes: "",
        medications: "",
        appointment: {
          id: this.appointmentSelected,
        }, 
      },
      errorMessages: {
        required: "Campo obligatorio",
        noneScripts: "Campo inválido no se aceptan scripts",
      },
    };
  },
  methods: {
    async saveRecords() {
      try {
        const result = await SweetAlertCustom.questionMessage();
        if (result.isConfirmed) {
          const resp = await recordsController.saveRecords({
            ...this.recordData,
            diagnosis: await encrypt(this.recordData.diagnosis),
            treatmentNotes: await encrypt(this.recordData.treatmentNotes),
            medications: await encrypt(this.recordData.medications),
          });
          const { error } = resp;
          if (!error) {
            this.$emit("realoadRegisters");
            setTimeout(() => {
              SweetAlertCustom.successMessage();
            }, 1000);
            this.v$.recordData.$reset();
            this.$bvModal.hide("save-records");
            return;
          }
        }
      } catch (error) {
        console.log(error);
      }
    },
    onClose(){
      this.recordData.appointment=null;
      this.recordData.diagnosis=null;
      this.recordData.medications=null;
      this.recordData.treatmentNotes=null;
      this.v$.recordData.$reset();
      this.$bvModal.hide("save-records");
    }
  },
  validations() {
    return {
      recordData: {
        diagnosis: {
          required: helpers.withMessage(this.errorMessages.required, required),
          minLength: helpers.withMessage("Mínimo 3 caracteres", minLength(3)),
          notScript: helpers.withMessage(
            this.errorMessages.noneScripts,
            (value) => {
              return !/<.*?script.*\/?>/gi.test(value);
            }
          ),
        },
        treatmentNotes: {
          required: helpers.withMessage(this.errorMessages.required, required),
          notScript: helpers.withMessage(
            this.errorMessages.noneScripts,
            (value) => {
              return !/<.*?script.*\/?>/gi.test(value);
            }
          ),
          minLength: helpers.withMessage("Mínimo 3 caracteres", minLength(3)),
        },
        medications: {
          required: helpers.withMessage(this.errorMessages.required, required),
          notScript: helpers.withMessage(
            this.errorMessages.noneScripts,
            (value) => {
              return !/<.*?script.*\/?>/gi.test(value);
            }
          ),
          minLength: helpers.withMessage("Mínimo 3 caracteres", minLength(3)),
        },
      },
    };
  },
};
</script>

<style scoped>
</style>
