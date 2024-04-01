import axios from '@/config/client.gateway'

export default {
    async verifyFriendlyCaptcha(solution){
        return axios.doPost('/captcha/verify-captcha', {solution})
            .then(response => response.data)
            .catch(error => {
                console.error('Error al verificar el captcha:', error);
                throw error;
            });
    }
}