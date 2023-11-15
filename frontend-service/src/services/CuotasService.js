import axios from 'axios';

const CUOTAS_API_URL = "http://localhost:8080/cuotas/";

class CuotasService {

    generarCuotas(rut, cuotas){
        return axios.get(CUOTAS_API_URL + rut +"/"+cuotas);
    }
    getCuotas(rut){
        return axios.get(CUOTAS_API_URL + rut);
    }

    pagar(id_cuota){
        return axios.put(CUOTAS_API_URL + "pagar/" + id_cuota)
    }

}
    
export default new CuotasService()