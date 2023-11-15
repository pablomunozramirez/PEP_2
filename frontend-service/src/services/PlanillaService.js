import axios from 'axios';

const PLANILLA_API_URL = "http://localhost:8080/arancel/";

const PRUEBA_API_URL = "http://localhost:8080/arancel/prueba/";

class PlanillaService {

    subirPruebas(file){
        return axios.post(PRUEBA_API_URL + "cargar", file);
    }

    mostrarPruebas(){
        return axios.get(PRUEBA_API_URL + "mostrarpruebas");
    }

    mostrarArancel(){
        axios.post(PLANILLA_API_URL + "generar");
        return axios.post(PLANILLA_API_URL + "mostrar");
    }

}

export default new PlanillaService()