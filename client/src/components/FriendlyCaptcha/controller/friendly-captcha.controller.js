import ApiServices from '../service/friendly-captcha.gateway';

export default {
    async verifyFriendlyCaptcha(solution){
        try {
            return await ApiServices.verifyFriendlyCaptcha(solution);
        } catch (error) {
            console.error(error);
            throw error;
        }
    }
}