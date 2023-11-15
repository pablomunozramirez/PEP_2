import React, { useState, useEffect } from "react";
import PlanillaService from '../services/PlanillaService'
import HeaderComponent from './Headers/HeaderComponent'

function ListadoPruebaComponent() {

    const [pruebaEntity, setPruebaEntity] = useState([]);
    useEffect(() => {
        PlanillaService.mostrarPruebas().then((res) => {
            console.log("Response data Prueba:", res.data);
            setPruebaEntity(res.data);
        });
    }, []);

    return (
        <div className="general">
            <HeaderComponent />
            <div align="center" className="container-2">
                <h1><b> Listado de Pruebas</b></h1>
                <table border="1" className="content-table">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Fecha del Examen</th>
                            <th>Puntaje</th>
                            <th>Rut</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            pruebaEntity.map((prueba) => (
                                <tr key={prueba.id_prueba}>
                                    <td> {prueba.id_prueba} </td>
                                    <td> {prueba.fecha_examen} </td>
                                    <td> {prueba.puntaje} </td>
                                    <td> {prueba.rutPrueba} </td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
            </div>
        </div>
    )
}

export default ListadoPruebaComponent