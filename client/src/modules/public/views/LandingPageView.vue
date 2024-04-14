<template>
  <div>
    <!--   Navbar  -->
    <Navbar/>

    <!--   Landing Page Content  -->
    <div class="landing-page-content">
      <div class="container">
        <div class="row align-items-center">
          <div class="col-md-6" data-aos="zoom-in-up">
            <h1 class="display-4 font-weight-bold" style="color: #10b981">Bienvenido a CIMI</h1>
            <p class="lead">CIMI es una plataforma de gestión de información médica que te permite llevar un control de
              tus citas médicas, historial clínico y mucho más.</p>
            <p class="my-3">Regístrate y comienza a disfrutar de los beneficios que CIMI tiene para ti.</p>
            <b-button
                :to="{name: 'login', query: { action: 'register' }}"
                variant="primary" size="lg" class="custom-button">Crear cuenta</b-button>
          </div>
          <div class="col-md-6 text-center" data-aos="zoom-in-up">
            <img src="@/assets/image/kit-doctor-profile.gif" alt="CIMI" class="img-fluid">
          </div>
        </div>
      </div>
    </div>

    <!--  group of cards  -->
    <div class="landing-page-card">
      <div class="container">
        <h2 class="text-center mb-4" data-aos="zoom-in-up"  data-aos-offset="300"   >Beneficios de CIMI</h2>
        <div class="row" data-aos="zoom-in-up"  data-aos-offset="300">
          <div class="col-md-4">
            <b-card title="Gestiona tus citas médicas"
                    img-src="https://img.freepik.com/foto-gratis/alegre-joven-medico-vistiendo-bata-medica-estetoscopio-gafas-sentados-frente-al-escritorio_141793-36330.jpg"
                    img-alt="Image"
                    img-top>
              <b-card-text>Con CIMI podrás llevar un control de tus citas médicas de manera fácil y rápida.
              </b-card-text>
            </b-card>
          </div>
          <div class="col-md-4">
            <b-card title="Historial clínico"
                    img-src="https://img.freepik.com/foto-gratis/vista-frontal-joven-medico-traje-blanco-estetoscopio-azul-anotando-notas_140725-29680.jpg"
                    img-alt="Image" img-top>
              <b-card-text>Guarda tu historial clínico en un solo lugar y accede a él cuando lo necesites.</b-card-text>
            </b-card>
          </div>
          <div class="col-md-4">
            <b-card title="Calidad de servicio"
                    img-src="https://img.freepik.com/foto-gratis/banner-medico-medico-estetoscopio_23-2149611228.jpg"
                    img-alt="Image" img-top>
              <b-card-text>En CIMI nos preocupamos por brindarte un servicio de calidad y seguro.</b-card-text>
            </b-card>
          </div>
        </div>
      </div>
    </div>

    <!--  image an another text  -->
    <div class="landing-page-flayer" style="position: relative;">

      <div class="bg-primary py-3 py-md-1 text-center text-white font-weight-bold h3 m-0 bar-text">
        Diariamente nos esforzamos por ser mejores para ti
      </div>

      <div class="container">
        <div class="row align-items-center">
          <div class="col-md-6">
            <img src="@/assets/image/doctors.svg" alt="CIMI" class="img-fluid" data-aos="zoom-in-right" data-aos-offset="150">
          </div>
          <div class="col-md-6 text-center">
            <h1 class="display-lg-4  display-md-5 font-weight-bold pt-3 pt-md-1"
                style="color: #10b981; min-height: 200px">Doctores especializados en cada área</h1>
          </div>
        </div>

      </div>
    </div>

    <!--  carrousel, image and text  -->
    <div class="landing-page-carrousel">
      <div class="container">
        <div id="carouselExampleControls" class="carousel slide" data-ride="carousel" data-aos="zoom-out" data-aos-offset="300">
          <div class="carousel-inner">
            <div class="carousel-item active">
              <b-card no-body class="mx-auto border-0" style="max-width: 90%;">
                <b-row no-gutters class="align-items-center">
                  <b-col md="6">
                    <b-card-img
                        src="https://img.freepik.com/foto-gratis/doctora-feliz-dando-alta-fie-nino-que-vino-su-padre-al-hospital_637285-492.jpg"
                        alt="Image" class="rounded-0  " height="300" style="object-fit: cover"></b-card-img>
                  </b-col>
                  <b-col md="6">
                    <b-card-body>
                      <b-card-text class="text-justify">
                        <b>El Hospital CIMI es el hospital comprometido con la medicina y los diversos plantamientos en
                          el área de la medicina, integra a profesionales de la salud, pioneros y expertos, dedicados a
                          cuidar de la salud por más de 50 años.</b>
                      </b-card-text>
                    </b-card-body>
                  </b-col>
                </b-row>
              </b-card>
            </div>
            <div class="carousel-item">
              <b-card no-body class="mx-auto border-0" style="max-width: 90%;">
                <b-row no-gutters class="align-items-center">
                  <b-col md="6">
                    <b-card-img
                        src="https://img.freepik.com/foto-gratis/fondo-borroso-interior-hermoso-hospital-clinica-lujo-hermoso-falta-definicion-fondo-imagenes-estilo-efecto-vintage_1253-1364.jpg"
                        alt="Image" class="rounded-0  " height="300" style="object-fit: cover"></b-card-img>
                  </b-col>
                  <b-col md="6">
                    <b-card-body>
                      <b-card-text class="text-justify">
                        <b>Siempre a la vanguardia en tecnología y servicios, el Hospital CIMI es el lugar donde la
                          medicina y la tecnología se unen para brindar un servicio de calidad y seguro.</b>
                      </b-card-text>
                    </b-card-body>
                  </b-col>
                </b-row>
              </b-card>
            </div>
          </div>
          <button class="carousel-control-prev" type="button" data-target="#carouselExampleControls" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
          </button>
          <button class="carousel-control-next" type="button" data-target="#carouselExampleControls" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
          </button>
        </div>

      </div>
    </div>

    <!--  steps to make an appointment  -->
    <div class="landing-page-steps">
      <div class="container">
        <h2 class="font-weight-bold" style="color: #10b981">
          Agendemos una cita y cuidemos de tu salud
        </h2>
        <h4 class="text-muted mb-5">
          Pasos para agendar una cita
        </h4>
        <div class="row justify-content-center">
          <div class="col-12 col-md-4 col-lg-4 col-xl-2 my-2">
            <div class="card position-relative h-100"  data-aos="flip-down">
              <h3>
                  <span class="position-absolute p-2 px-4 text-light badge rounded-pill bg-primary"
                        data-aos-offset="200" data-aos="fade-down" style="top: -15px; left: -15px">1
                  <span class="visually-hidden d-none">step 1</span>
                </span>
              </h3>
              <div class="card-body d-flex align-items-center">
                <h5 class="card-title">Crear una cuenta con tus datos personales</h5>
              </div>
            </div>
          </div>
          <div class="col-12 col-md-4 col-lg-4 col-xl-2 my-2">
            <div class="card position-relative h-100" data-aos="flip-down">
              <h3>
                  <span class="position-absolute p-2 px-4 text-light badge rounded-pill bg-primary"
                        data-aos-offset="200" data-aos="fade-down" style="top: -15px; left: -15px">2
                  <span class="visually-hidden d-none">step 2</span>
                </span>
              </h3>
              <div class="card-body d-flex align-items-center">
                <h5 class="card-title ">Selecciona el doctor de la especialidad que necesitas</h5>
              </div>
            </div>
          </div>
          <div class="col-12 col-md-4 col-lg-4 col-xl-2 my-2">
            <div class="card position-relative h-100" data-aos="flip-down">
              <h3>
                  <span class="position-absolute p-2 px-4 text-light badge rounded-pill bg-primary"
                        data-aos-offset="200" data-aos="fade-down" style="top: -15px; left: -15px">3
                  <span class="visually-hidden d-none">step 3</span>
                </span>
              </h3>
              <div class="card-body d-flex align-items-center">
                <h5 class="card-title">Selecciona la fecha y hora de tu cita</h5>
              </div>
            </div>
          </div>
          <div class="col-12 col-md-4 col-lg-4 col-xl-2 my-2">
            <div class="card position-relative h-100" data-aos="flip-down">
              <h3>
                  <span class="position-absolute p-2 px-4 text-light badge rounded-pill bg-primary"
                        data-aos-offset="200" data-aos="fade-down" style="top: -15px; left: -15px">4
                  <span class="visually-hidden d-none">step 4</span>
                </span>
              </h3>
              <div class="card-body d-flex align-items-center">
                <h5 class="card-title">Realiza el pago de tu cita</h5>
              </div>
            </div>
          </div>
          <div class="col-12 col-md-4 col-lg-4 col-xl-2 my-2">
            <div class="card position-relative h-100" data-aos="flip-down">
              <h3>
                  <span class="position-absolute p-2 px-4 text-light badge rounded-pill bg-primary"
                        data-aos-offset="200" data-aos="fade-down" style="top: -15px; left: -15px">5
                  <span class="visually-hidden d-none">step 5</span>
                </span>
              </h3>
              <div class="card-body d-flex align-items-center">
                <h5 class="card-title">Recibe la confirmación de tu cita y listo</h5>
              </div>
            </div>
          </div>
        </div>
        <!--        button create account-->
        <div class="text-center mt-5">
          <b-button
              :to="{name: 'login', query: { action: 'register' }}"
              variant="primary" size="lg" class="custom-button w-75">Crear cuenta</b-button>
        </div>
      </div>
    </div>

    <!--  certifications  -->
    <div class="landing-page-certification">
      <h2 class="text-center mb-4 py-2 display-5 text-primary font-weight-bold" data-aos="zoom-in-up" data-aos-offset="300">
        Certificaciones</h2>

      <div class="container">
        <div class="row" data-aos="zoom-in-up" data-aos-offset="300">
          <div class="col-12 col-md-3">
            <div class="card">
              <div class="card-body text-center">
                <h6 class="card-title">
                  UNE – EN ISO 22000 Sistema de Gestión de la Seguridad Alimentaria: Esta norma se aplica a la
                  seguridad alimentaria en el hospital.
                </h6>
                <svg xmlns="http://www.w3.org/2000/svg" width="40" fill="currentColor"
                     class="text-primary bi bi-patch-check-fill" viewBox="0 0 16 16">
                  <path
                      d="M10.067.87a2.89 2.89 0 0 0-4.134 0l-.622.638-.89-.011a2.89 2.89 0 0 0-2.924 2.924l.01.89-.636.622a2.89 2.89 0 0 0 0 4.134l.637.622-.011.89a2.89 2.89 0 0 0 2.924 2.924l.89-.01.622.636a2.89 2.89 0 0 0 4.134 0l.622-.637.89.011a2.89 2.89 0 0 0 2.924-2.924l-.01-.89.636-.622a2.89 2.89 0 0 0 0-4.134l-.637-.622.011-.89a2.89 2.89 0 0 0-2.924-2.924l-.89.01zm.287 5.984-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 1 1 .708-.708L7 8.793l2.646-2.647a.5.5 0 0 1 .708.708"/>
                </svg>
              </div>
            </div>
          </div>
          <div class="col-12 col-md-3">
            <div class="card">
              <div class="card-body text-center">
                <h6 class=" mb-4">ISO 45001 Sistema de Gestión de la Seguridad y Salud en el Trabajo: Esta
                  certificación se enfoca en la seguridad y salud de los trabajadores.</h6>
                <svg xmlns="http://www.w3.org/2000/svg" width="40" fill="currentColor"
                     class="text-primary bi bi-patch-check-fill" viewBox="0 0 16 16">
                  <path
                      d="M10.067.87a2.89 2.89 0 0 0-4.134 0l-.622.638-.89-.011a2.89 2.89 0 0 0-2.924 2.924l.01.89-.636.622a2.89 2.89 0 0 0 0 4.134l.637.622-.011.89a2.89 2.89 0 0 0 2.924 2.924l.89-.01.622.636a2.89 2.89 0 0 0 4.134 0l.622-.637.89.011a2.89 2.89 0 0 0 2.924-2.924l-.01-.89.636-.622a2.89 2.89 0 0 0 0-4.134l-.637-.622.011-.89a2.89 2.89 0 0 0-2.924-2.924l-.89.01zm.287 5.984-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 1 1 .708-.708L7 8.793l2.646-2.647a.5.5 0 0 1 .708.708"/>
                </svg>
              </div>
            </div>
          </div>
          <div class="col-12 col-md-3">
            <div class="card">
              <div class="card-body text-center">
                <h6 class="card-title">Norma ISO 9001 para Hospitales: Esta norma se centra en la calidad de los
                  servicios que ofrece un hospital.</h6>
                <svg xmlns="http://www.w3.org/2000/svg" width="40" fill="currentColor"
                     class="text-primary bi bi-patch-check-fill" viewBox="0 0 16 16">
                  <path
                      d="M10.067.87a2.89 2.89 0 0 0-4.134 0l-.622.638-.89-.011a2.89 2.89 0 0 0-2.924 2.924l.01.89-.636.622a2.89 2.89 0 0 0 0 4.134l.637.622-.011.89a2.89 2.89 0 0 0 2.924 2.924l.89-.01.622.636a2.89 2.89 0 0 0 4.134 0l.622-.637.89.011a2.89 2.89 0 0 0 2.924-2.924l-.01-.89.636-.622a2.89 2.89 0 0 0 0-4.134l-.637-.622.011-.89a2.89 2.89 0 0 0-2.924-2.924l-.89.01zm.287 5.984-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 1 1 .708-.708L7 8.793l2.646-2.647a.5.5 0 0 1 .708.708"/>
                </svg>
              </div>
            </div>
          </div>
          <div class="col-12 col-md-3">
            <div class="card">
              <div class="card-body text-center">
                <h6 class="card-title
                ">ISO 14001 Sistema de Gestión Ambiental: Esta certificación se enfoca en la gestión ambiental del
                  hospital.</h6>
                <svg xmlns="http://www.w3.org/2000/svg" width="40" fill="currentColor"
                     class="text-primary bi bi-patch-check-fill" viewBox="0 0 16 16">
                  <path
                      d="M10.067.87a2.89 2.89 0 0 0-4.134 0l-.622.638-.89-.011a2.89 2.89 0 0 0-2.924 2.924l.01.89-.636.622a2.89 2.89 0 0 0 0 4.134l.637.622-.011.89a2.89 2.89 0 0 0 2.924 2.924l.89-.01.622.636a2.89 2.89 0 0 0 4.134 0l.622-.637.89.011a2.89 2.89 0 0 0 2.924-2.924l-.01-.89.636-.622a2.89 2.89 0 0 0 0-4.134l-.637-.622.011-.89a2.89 2.89 0 0 0-2.924-2.924l-.89.01zm.287 5.984-3 3a.5.5 0 0 1-.708 0l-1.5-1.5a.5.5 0 1 1 .708-.708L7 8.793l2.646-2.647a.5.5 0 0 1 .708.708"/>
                </svg>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!--  opinion of the users  -->
    <div class="landing-page-opinion">
      <h2 class="text-center mb-4  py-2 display-5 text-primary font-weight-bold" data-aos="zoom-in-up" data-aos-offset="300">
        Opiniones de nuestros usuarios</h2>
      <div class="container">
        <div id="carouselExampleControls2" class="carousel slide" data-ride="carousel" data-aos="zoom-out" data-aos-offset="300">
          <div class="carousel-inner">
            <div class="carousel-item active">
              <b-card no-body class="mx-auto border-0" style="max-width: 90%;">
                <b-row no-gutters class="align-items-center">
                  <b-col md="6">
                    <b-card-img
                        src="https://img.freepik.com/foto-gratis/doctor-ayudando-al-paciente-rehabilitacion_23-2150321576.jpg"
                        alt="Image" class="rounded-0  " height="300" style="object-fit: cover"></b-card-img>
                  </b-col>
                  <b-col md="6">
                    <b-card-body>
                      <b-card-text class="text-justify">
                        <b>Los servicios de CIMI son excelentes, con doctores especializados en cada área y un trato
                          amable y profesional.</b>
                        <p>
                          <b class="text-muted
                          "> - Juan Pérez</b>
                        </p>
                      </b-card-text>
                    </b-card-body>
                  </b-col>
                </b-row>
              </b-card>
            </div>
            <div class="carousel-item ">
              <b-card no-body class="mx-auto border-0 " style="max-width: 90%;">
                <b-row no-gutters class="align-items-center">
                  <b-col md="6">
                    <b-card-img
                        src="https://img.freepik.com/foto-gratis/smiley-covid-recovery-center-doctora-sosteniendo-manos-paciente-anciano_23-2148847831.jpg"
                        alt="Image" class="rounded-0  " height="300" style="object-fit: cover"></b-card-img>
                  </b-col>
                  <b-col md="6">
                    <b-card-body>
                      <b-card-text class="text-justify ">
                        <b>Me encanta CIMI, es una plataforma muy fácil de usar y me ha ayudado a llevar un control de
                          mis
                          citas médicas y mi historial clínico.</b>
                        <p>
                          <b class="text-muted"> - María Pérez</b>
                        </p>
                      </b-card-text>
                    </b-card-body>
                  </b-col>
                </b-row>
              </b-card>
            </div>
          </div>
          <button class="carousel-control-prev" type="button" data-target="#carouselExampleControls2" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
          </button>
          <button class="carousel-control-next" type="button" data-target="#carouselExampleControls2" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
          </button>
        </div>

      </div>

    </div>

    <!--    set map google-->
    <div class="landing-page-map">
      <!--      set a point on a map-->
      <iframe
          src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d60394.41765076789!2d-99.25014595136717!3d18.90255320000001!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x85cddffcc87c2263%3A0x8f579b11f8a23d75!2sFarmacias%20Similares!5e0!3m2!1ses!2smx!4v1711500822061!5m2!1ses!2smx"
          width="100%" height="50%" style="border:0;" allowfullscreen="" loading="lazy"
          referrerpolicy="no-referrer-when-downgrade" title="maps"></iframe>

    </div>

    <!--    Footer  -->
    <div class="bg-primary">
      <div class="px-4 py-5">
        <div class="row">
          <div class="col-12 col-md-3">
            <h3 class="text-white" >Acerca de nosotros</h3>
            <!--          logo-->
            <img src="@/assets/image/doctor_3584957.png" alt="CIMI" height="40" class="my-3 img-fluid" style="max-width: 20%"  >
            <p class="text-white">
              CIMI es una plataforma de gestión de información médica que te permite llevar un control de tus citas médicas, historial clínico y mucho más.
            </p>
            <p class="text-white">
              Centro comercial, 1234, Ciudad de México, México
            </p>
          </div>
          <div class="col-12 col-md-3">
            <h3 class="text-white mb-5" >Etiquetas</h3>
            <h5 class="d-flex"><b-badge variant="light">Cuidado medico</b-badge></h5>
            <h5 class="d-flex"><b-badge variant="light">Historial clínico</b-badge></h5>
            <h5 class="d-flex"><b-badge variant="light">Calidad de servicio</b-badge></h5>
            <h5 class="d-flex"><b-badge variant="light">Doctores especializados</b-badge></h5>
          </div>
          <div class="col-12 col-md-3">
            <h3 class="text-white mb-3" >Enlaces rapidos</h3>
            <p class="d-flex"><b-link class="text-white"
                :to="{name: 'login'}">Iniciar Sesión</b-link></p>
            <p class="d-flex"><b-link class="text-white"
                :to="{name: 'login', query: { action: 'register' }}">Crear cuenta</b-link></p>
            <p class="d-flex"><b-link class="text-white" >Nuestros servicios</b-link></p>

          </div>
          <div class="col-12 col-md-3">
            <h3 class="text-white mb-3" >Haz una cita</h3>
            <p class="text-white"> Al hacer una cita con nosotros, te comprometes a seguir las medidas de seguridad e higiene establecidas por el hospital.</p>

            <p class="text-white">Lunes a viernes: 8:00 am - 8:00 pm</p>
            <p class="text-white">Sábados: 8:00 am - 2:00 pm</p>

<!--            email and phone-->
            <p class="text-white">Correo:
              <a href="mailto:cimi@gmail.com" class="text-white">
                cimi@gmail.com
              </a>
            </p>
            <p class="text-white">Teléfono:
              <a href="tel:+525555555555" class="text-white">
                +52 555 555 5555
              </a>
            </p>

          </div>
        </div>

      </div>
    </div>

  </div>
</template>
<script>
import { jwtDecode } from "jwt-decode";
import { ERoles } from "../../../kernel/types";
export default {
  name: 'LandingPageView',
  components: {
    Navbar: () => import('@/modules/public/components/Navbar.vue'),
  },
}
</script>

<style scoped>
.landing-page-content {
  padding: 100px 0;
  height: 95vh;
  background-color: #f8f9fa;
}


.landing-page-flayer {
  background-color: #f8f9fa;
  padding: 100px 0 0 0;
}

.landing-page-card {
  padding: 50px 0;

}

.landing-page-carrousel {
  padding: 50px 0;
  margin: 80px 0;
}

.landing-page-steps {
  background-color: #f8f9fa;
  padding: 80px 0;
  margin-bottom: 60px;
}

.landing-page-certification {
  padding: 80px 0;
  margin-bottom: 60px;
}

.landing-page-opinion {
  padding: 80px 0;
  background-color: #f8f9fa;

}

.bar-text {
  position: absolute;
  width: 100%;
  bottom: 0;
  z-index: 1;
}

@media (max-width: 768px) {
  .landing-page-content {
    padding: 10px 0;
  }

  [alt="CIMI"] {
    margin: 0 auto;
    padding: 0 0;
    width: 80%;
  }

  .bar-text {
    font-size: 1rem;
    bottom: 0;
    padding: 10px 0;
  }
}

@media (max-width: 991px) {
  .custom-button {
    width: 100%;
  }

  .landing-page-content {
    padding: 40px 0;
  }


}
</style>