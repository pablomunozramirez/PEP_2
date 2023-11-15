import React, { useState } from "react";
import PlanillaService from "../services/PlanillaService";
import { useNavigate } from "react-router-dom";
import HeaderComponent from './Headers/HeaderComponent';
import "../styles/main.css";
import "../styles/subir_excel.css";

function SubirPruebaComponents() {
    const navigate = useNavigate();
    const [archivo, setArchivo] = useState(null);

    const handleFileChange = (event) => {
        const file = event.target.files[0];
        setArchivo(file);
    };

    const handleUpload = () => {
        if (archivo) {
            const formData = new FormData();
            formData.append('file', archivo);

            PlanillaService.subirPruebas(formData)
                .then((response) => {
                    console.log("Archivo subido exitosamente:", response.data);
                    // Navegar a la lista de pruebas después de la carga exitosa
                    navigate('/mostrar_prueba');
                })
                .catch((error) => {
                    console.error("Error al subir el archivo:", error);
                });
        }
    };

    return (
        <div>
            <HeaderComponent />
            <div className="contenedor">
                <div className="center-top-container">
                    <h1>Subir Archivo de Prueba</h1>
                    <form encType="multipart/form-data">
                        <input type="file" onChange={handleFileChange} />
                        <button className="Generar" type="button" onClick={handleUpload}>
                            Subir Archivo
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default SubirPruebaComponents;
