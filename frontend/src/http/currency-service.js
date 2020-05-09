import http from './common'

class CurrencyService {

    getAllAvailablePriceSymbols() {
      return http.get("/currency");
    }

}  

export default new CurrencyService();
