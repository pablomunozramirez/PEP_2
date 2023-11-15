import React from "react";
import "./App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import MainComponent from "./components/MainComponent";
import AgregarEstudianteComponent from "./components/AgregarEstudianteComponent";
import ListadoEstudianteComponent from "./components/ListadoEstudianteComponent";
import GenerarCuotasComponent from "./components/GenerarCuotasComponent";
import ListadoCuotasComponent from "./components/ListadoCuotasComponent";
import SubirPruebaComponent from "./components/SubirPruebaComponent";
import MostrarPurebasComponent from "./components/MostrarPurebasComponent";
import ListadoArancelComponent from "./components/ListadoArancelComponent";

function App() {
    return (
        <div>
            <Router>
                <Routes>
                    <Route path="/" element={<MainComponent />} />
                    <Route path="/agregar_estudiante" element={<AgregarEstudianteComponent />} />
                    <Route path="/lista_estudiantes" element={<ListadoEstudianteComponent />} />
                    <Route path="/generar_cuotas" element={<GenerarCuotasComponent />} />
                    <Route path="/lista_cuotas" element={<ListadoCuotasComponent />} />
                    <Route path="/subir_prueba" element={<SubirPruebaComponent />} />
                    <Route path="/mostrar_prueba" element={<MostrarPurebasComponent />} />
                    <Route path="/resumen" element={<ListadoArancelComponent />} />
                </Routes>
            </Router>
        </div>
    );
}

export default App;
