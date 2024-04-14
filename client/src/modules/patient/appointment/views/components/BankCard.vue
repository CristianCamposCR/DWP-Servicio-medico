<template>

  <div class="cardCustom ">
    <div class="cardCustom-inner">
      <div class="cardCustom-front">
        <div class="cardCustom-bg"></div>
        <div class="cardCustom-glow"></div>
        <svg
            width="72"
            height="24"
            viewBox="0 0 72 24"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
            class="logo"
        >

        </svg>
        <div class="cardCustom-contactless">
          <svg xmlns="http://www.w3.org/2000/svg" width="46" height="56">
            <path
                fill="none"
                stroke="#f9f9f9"
                stroke-width="6"
                stroke-linecap="round"
                d="m35,3a50,50 0 0,1 0,50M24,8.5a39,39 0 0,1 0,39M13.5,13.55a28.2,28.5
  0 0,1 0,28.5M3,19a18,17 0 0,1 0,18"
            />
          </svg>
        </div>

        <div class="cardCustom-chip"></div>
        <div class="row px-4 d-flex justify-content-end align-items-end position-absolute" style="bottom: 20px;">
          <div class="text-white col-12">
            {{ cardNumber ? cardNumber : '•••• •••• •••• ••••' }}
          </div>
          <div class="text-white col-12 text-break">{{ cardHolder ? cardHolder : 'TITULAR DE LA TARJETA' }}</div>
          <div class="text-white col-12">
            <p class="text-white m-0" style="font-size: 10px">VALID THRU / VENCIMIENTO</p>
            <slot name="validDate"></slot>
          </div>
        </div>
      </div>
      <div class="cardCustom-back">
        <div class="cardCustom-signature">{{
            cardHolder?.split(" ")[0].slice(0, 1)
          }}{{
            cardHolder?.split(" ")[1]?.slice(0, 1)
          }}
        </div>
        <div class="cardCustom-seccode">
          <slot name="cvv"></slot>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "BankCard",
  props: {
    cardNumberP: {
      type: String,
      default: "####",
    },
    cardHolderP: {
      type: String,
      default: 'TITULAR DE LA TARJETA',
    },

  },
  data() {
    return {
      cardNumber: this.cardNumberP,
      cardHolder: this.cardHolderP,
    };
  },
  watch: {
    cardNumberP: function (val) {

      this.cardNumber = val;
      let less = [];
      for (let i = val.length; i   < 16; i ++) less.push('•')
      this.cardNumber = val + less.join('');
      const aux = this.cardNumber.match(/.{1,4}/g)
      for (let i = 1; i < aux.length; i++) {
        if (aux[i][0] !== '•') aux[i-1] = '••••'
      }
      this.cardNumber = aux.join(' ');
    },
    cardHolderP: function (val) {
      this.cardHolder = val.toUpperCase();
    },
  },
};
</script>

<style lang="scss">

.cardCustom {
  width: 100%;
  height: 200px;
  border-radius: 10px;
  perspective: 1000px;

  &:hover .cardCustom-inner {
    transform: rotateY(180deg);
  }

  &-inner {
    position: relative;
    width: 100%;
    height: 100%;
    border-radius: 10px;
    transition: transform 600ms ease;
    transform-style: preserve-3d;
    box-shadow: 0 0 25px 2px rgba(black, 0.2);
  }

  &-front,
  &-back {
    position: absolute;
    top: 0;
    width: 100%;
    height: 100%;
    border-radius: 10px;
    overflow: hidden;
    backface-visibility: hidden;
    background: linear-gradient(321.03deg, #545f64 0%, #6d86a1 91.45%);
  }

  &-front {
    border-radius: 10px;
    overflow: hidden;
    position: relative;
    transition: transform 300ms ease-in-out;
  }

  &-back {
    transform: rotateY(180deg);

    &::before {
      content: "";
      position: absolute;
      top: 40%;
      left: 20%;
      width: 180%;
      height: 120%;
      border-radius: 100%;
      background-image: linear-gradient(
              to right top,
              #424648,
              #373c3d,
              #394042,
              #474c4d,
              #40484b,
              #57656b,
              #3e4649,
              #414a4d,
              #292f31,
              #283033,
              #222d31,
              #172328
      );
      filter: blur(10px);
      opacity: 0.15;
    }

    &::after {
      content: "";
      position: absolute;
      top: 15%;
      width: 100%;
      height: 40px;
      background-image: linear-gradient(
              to right top,
              #021318,
              #07191f,
              #0a1f26,
              #0b262e,
              #0c2c35,
              #0c2c35,
              #0c2c35,
              #0c2c35,
              #0b262e,
              #0a1f26,
              #07191f,
              #021318
      );
    }
  }

  &-bg {
    position: absolute;
    top: -20px;
    right: -120px;
    width: 380px;
    height: 250px;
    background: linear-gradient(321.03deg, #434d50 0%, #3d4d5e 91.45%);
    border-top-left-radius: 100%;

    &::before {
      content: "";
      position: absolute;
      top: -20px;
      right: -80px;
      width: 380px;
      height: 250px;
      background: linear-gradient(321.03deg, #454b4d 0%, #4a5664 91.45%);
      border-top-left-radius: 100%;
    }

    &::after {
      content: "";
      position: absolute;
      top: -20px;
      right: -120px;
      width: 380px;
      height: 250px;
      background: linear-gradient(321.03deg, #282c2d 0%, #4b5056 91.45%);
      border-top-left-radius: 100%;
    }
  }

  &-glow {
    position: absolute;
    top: -140px;
    left: -65px;
    height: 200px;
    width: 400px;
    background: rgba(#222, 0.2);
    filter: blur(10px);
    border-radius: 100%;
    transform: skew(-15deg, -15deg);
  }

  &-contactless {
    position: absolute;
    right: 15px;
    top: 55px;
    transform: scale(0.5);
  }

  &-chip {
    position: absolute;
    top: 30px;
    left: 25px;
    width: 45px;
    height: 34px;
    border-radius: 5px;
    background-color: #ffda7b;
    overflow: hidden;

    &::before {
      content: "";
      position: absolute;
      left: 49%;
      top: -7%;
      transform: translateX(-50%);
      background: #ffda7b;
      border: 1px solid #a27c1f;
      width: 25%;
      height: 110%;
      border-radius: 100%;
      z-index: 2;
    }

    &::after {
      content: "";
      position: absolute;
      top: 30%;
      left: -10%;
      background: transparent;
      border: 1px solid #a27c1f;
      width: 120%;
      height: 33%;
    }
  }

  &-holder {
    width: 200px;
    position: absolute;
    left: 25px;
    bottom: 22px;
    color: white;
    font-size: 14px;
    letter-spacing: 0.2em;
    filter: drop-shadow(1px 1px 1px rgba(black, 0.3));
  }

  &-number {
    position: absolute;
    left: 25px;
    bottom: 65px;
    color: white;
    font-size: 16px;
    font-weight: 600;
    letter-spacing: 0.2em;
    filter: drop-shadow(1px 1px 1px rgba(black, 0.3));
  }

  &-valid {
    //position: absolute;
    //right: 25px;
    //bottom: 30px;
    //color: white;
    //font-size: 14px;
    //letter-spacing: 0.2em;
    //filter: drop-shadow(1px 1px 1px rgba(black, 0.3));

    &::before {
      content: "VALID THRU / VENCIMIENTO";
      position: absolute;
      top: 3px;
      left: -85px;
      width: 80px;
      font-size: 7px;
    }
  }

  &-signature {
    position: absolute;
    top: 120px;
    left: 15px;
    width: 70%;
    height: 30px;
    background: rgb(238, 236, 236);
    display: flex;
    justify-content: center;
    align-items: center;
    color: #021318;
    font-family: "Mr Dafoe", cursive;
    font-size: 25px;
    font-weight: 400;

    &::before {
      content: "Authorized Signature";
      position: absolute;
      top: -15px;
      left: 0;
      font-family: "Overpass Mono", monospace;
      font-size: 9px;
      color: rgb(238, 236, 236);
    }
  }

  &-seccode {
    position: absolute;
    top: 125px;
    left: 75%;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 40px;
    height: 17px;
    color: #021318;
    background-color: rgb(238, 236, 236);
    text-align: center;
    font-size: 11px;
  }
}

.logo {
  position: absolute;
  right: 25px;
  top: 30px;
}

.hint {
  padding: 2em 0;
  font-family: "Noto Sans KR", sans-serif;
  letter-spacing: 0.025em;
  font-weight: 400;
  color: #a3d4e7;
}


</style>




