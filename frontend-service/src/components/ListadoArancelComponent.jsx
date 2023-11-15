import React, { useState, useEffect } from "react";
import PlanillaService from '../services/PlanillaService'
import HeaderComponent from './Headers/HeaderComponent'

function ArancelComponent() {

    const [arancelEntity, setArancelEntity] = useState([]);
    useEffect(() => {
        PlanillaService.mostrarArancel().then((res) => {
            console.log("Response data Arancel:", res.data);
            setArancelEntity(res.data);
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
                            <th>Rut Estudiante</th>
                            <th>Examenes rendidos</th>
                            <th>Promedio examenes</th>
                            <th>Monto total Arancel</th>
                            <th>Tipo de Pago</th>
                            <th>Numero de cuotas</th>
                            <th>Monto Pagado</th>
                            <th>Fecha del ultimo pago</th>
                            <th>Dinero por Pagar</th>
                            <th>Numero de cuotas con retraso</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            arancelEntity.map((arancel) => (
                                <tr key={arancel.idResumen}>
                                    <td> {arancel.rutEstudiante} </td>
                                    <td> {arancel.numeroExamenesRendidos} </td>
                                    <td> {arancel.promedioExamenes} </td>
                                    <td> {arancel.montoTotalArancel} </td>
                                    <td> {arancel.tipoPago} </td>
                                    <td> {arancel.numeroTotalCuotas} </td>
                                    <td> {arancel.montoTotalPagado} </td>
                                    <td> {arancel.fechaUltimoPago} </td>
                                    <td> {arancel.saldoPorPagar} </td>
                                    <td> {arancel.numeroCuotasConRetraso} </td>
                                </tr>
                            ))
                        }
                    </tbody>
                </table>
            </div>
        </div>
    )
}

export default ArancelComponent