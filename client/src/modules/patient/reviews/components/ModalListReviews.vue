<template>
  <b-modal id="modal-review" scrollable title="Puntuaciones y Comentarios" v-model="isVisible"
           @close="close" @hide="close" @hidden="close"  @cancel="()=>{this.close() }" centered hide-footer
  >

    <p>
      Hola! Que tal te fue en tu última cita? Nos gustaría saber tu opinión para mejorar nuestros servicios.
    </p>


    <div v-for="item in list" :key="item.id">


      <b-card no-body>
        <b-card-header  style="cursor: pointer" @click="e => handleToggle(item.id, e)" class="bg-transparent">
          <p class="m-0  text-primary ">
            {{
            formatStyleText(item.speciality.name, 'uppercase')
            }}
            -
            {{
            formatStyleText(item.doctor, 'uppercase')
            }}
          </p>
          <sub>
            {{ item.status.name }} - Dia de la cita: {{ formatDate(item.scheduledAt) }}, {{ item.preferentialShift.name }}
          </sub>
        </b-card-header  >
        <b-collapse :visible="collapse.item === item.id">
          <b-card-body>
          <b-form>
            <b-form-group>
              <template #label>
                Puntuación
              </template>
              <b-form-rating
                  icon-empty="heart"
                  icon-half="heart-half"
                  icon-full="heart-fill"
                  variant="danger"
                  v-model="item.ranking"
              ></b-form-rating>
            </b-form-group>

            <b-form-group>
              <template #label>
                Comentarios
              </template>

              <b-form-textarea
                  id="textarea"
                  placeholder="Tu opinión..."
                  rows="3"
                  maxlength="100"
                  max-rows="6"
                  v-model="item.comment"
                  :state="!item.comment ? null : !validateTextArea(item.comment)"
              ></b-form-textarea>

              <b-form-invalid-feedback>
                {{ validateTextArea(item.comment) }}
              </b-form-invalid-feedback>
            </b-form-group>


            <div class="text-right">
              <b-button variant="primary" class="mr-2" @click="ok(item, true)">Enviar</b-button>
              <b-button variant="secondary" @click="ok(item, false)">Cerrar</b-button>
            </div>

          </b-form>
        </b-card-body>
        </b-collapse>

      </b-card>
    </div>

  </b-modal>
</template>

<script>
import {formatDate, formatStyleText} from "@/kernel/functions";
import SweetAlertCustom from "@/kernel/SweetAlertCustom";

export default {
  name: 'TermAndConditionView',
  props: {
    show: {
      type: Boolean,
      required: true
    },
    list: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      isVisible: this.show,
      collapse: {
        item: 1 ,
      }
    }
  },
  watch: {
    show(val) {
      this.isVisible = val
    }
  },
  methods: {
    formatStyleText,
    formatDate,
    validateTextArea(value) {
      if (value){
        if (!(/^[a-zA-Z0-9 ÁÉÍÓÚáéíóúñÑäëïöü,.¿?¡!"'\s]+$/.test(value))) {
          return 'Campo inválido, no se permiten caracteres especiales'
        }
      }
      return null
    },
    close() {
      // this.$emit('close')
    },
    ok(iteam, ok) {
    if (ok){
      if (iteam.comment && this.validateTextArea(iteam.comment) != null || isNaN(iteam.ranking)){
        SweetAlertCustom.invalidForm()
      }
    }

      this.$emit('saveReview', {
        comment: iteam.comment,
        ranking: iteam.ranking,
        wasSkipped: true,
        appointment:{
          id: iteam.id
        }
      }, ok)
      this.$emit('updateList')
    },
    handleToggle(index, event) {
      event.preventDefault();
      this.collapse.item = this.collapse.item === index ? -1 : index;
    },
  }
}

</script>
