import React from "react";
import { useNavigate } from "react-router-dom";
import "../styles/main.css";
import "../styles/listas.css";
import "../styles/subir_excel.css";
import "../styles/navbar.css";
import "../styles/agregar.css";
import Estudiantes from "../images/estudiantes.png"
import ListaCuotas from "../images/plan-de-estudios.png"
import Cuota from "../images/grafico-circular.png"
import Prueba from "../images/upload.png"
import HeaderComponent from "./Headers/HeaderComponent";

function MainComponents() {
    const navigate = useNavigate();
    const handleClickListaEstudiantes = () => {
        navigate("/lista_estudiantes");
    };
    const handleClickGenerarCuotas = () => {
        navigate("/generar_cuotas");
    };
    const handleClickListaCuotas = () => {
        navigate("/lista_cuotas");
    };

    const handleSubirPrueba = () => {
        navigate("/subir_prueba");
    };
    const handleMostrarPrueba = () => {
        navigate("/mostrar_prueba");
    };
    const handleResumen = () => {
        navigate("/resumen");
    };

    return (
        <div>
            <HeaderComponent></HeaderComponent>
            <div className="container_main">
                <div className="card" onClick={handleClickListaEstudiantes}>
                    <img id="lista_proveedores" src={Estudiantes} alt="Imagen_1" />
                    <h2>Listado de Estudiantes</h2>
                </div>
                <div className="card" onClick={handleClickGenerarCuotas}>
                    <img id="generar_cuotas" src={Cuota} alt="Imagen_2" />
                    <h2>Generar Cuotas</h2>
                </div>
                <div className="card" onClick={handleClickListaCuotas}>
                    <img id="lista_cuotas" src={ListaCuotas} alt="Imagen_3" />
                    <h2>Listado de Cuotas</h2>
                </div>
                <div className="card" onClick={handleSubirPrueba}>
                    <img id="subir_prueba" src={Prueba} alt="Imagen_4" />
                    <h2>Subir Prueba</h2>
                </div>
                <div className="card" onClick={handleMostrarPrueba}>
                    <img id="mostrar_prueba" src={ListaCuotas} alt="Imagen_3" />
                    <h2>Listado de Pruebas</h2>
                </div>
                <div className="card" onClick={handleResumen}>
                    <img id="aranceles" src={ListaCuotas} alt="Imagen_3" />
                    <h2>Resumen Aranceles</h2>
                </div>
            </div>
        </div>
    );
}

export default MainComponents;
