import React, { useState, useEffect } from "react";
import EstudianteService from "../services/EstudianteService";
import HeaderComponent from "./Headers/HeaderComponent";
import CuotasService from "../services/CuotasService";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

function ListadoCuotasComponent() {
    const [cuotasEntity, setCuotasEntity] = useState([]);
    const [rut, setRut] = useState("");
    const [rutParaBuscar, setRutParaBuscar] = useState("");
    const [estadoPagos, setEstadoPagos] = useState({});

    useEffect(() => {
        if (rutParaBuscar.trim() !== "") {
            getCuotasByRut();
        }
    }, [rutParaBuscar]);

    const getCuotasByRut = () => {
        CuotasService.getCuotas(rutParaBuscar).then((res) => {
            console.log("Response data Cuotas:", res.data);
            setCuotasEntity(res.data);
            setEstadoPagos(
                res.data.reduce((acc, cuota) => {
                    acc[cuota.numeroCuota] = cuota.pagada;
                    return acc;
                }, {})
            );
        });
    };

    const changeRutHandler = (event) => {
        setRut(event.target.value);
    };

    const buscarCuotas = () => {
        setRutParaBuscar(rut);
    };

    const pagar = (idCuota) => {
        console.log(`Pagar cuota ${idCuota}`);
        CuotasService.pagar(idCuota)
            .then((response) => {
                console.log("Cuota pagada exitosamente");
                getCuotasByRut();
            })
            .catch((error) => {
                console.error("Error al pagar la cuota", error);
            });
    };

    return (
        <div className="general">
            <HeaderComponent />
            <div align="center" className="agregar">
                <h1>
                    <b>Listado de Cuotas</b>
                </h1>
                <Form.Group className="mb-3" controlId="rut">
                    <Form.Label>Rut:</Form.Label>
                    <Form.Control
                        type="text"
                        name="rut"
                        className="agregar"
                        value={rut}
                        onChange={changeRutHandler}
                    />
                </Form.Group>
                <Button className="boton" variant="primary" onClick={buscarCuotas}>
                    Buscar
                </Button>
                <table border="1" className="content-table">
                    <thead>
                        <tr>
                            <th>Número Cuota</th>
                            <th>Monto</th>
                            <th>Estado</th>
                            <th>Fecha Cuota</th>
                            <th>Fecha Vencimiento</th>
                        </tr>
                    </thead>
                    <tbody>
                        {cuotasEntity.map((cuota) => (
                            <tr key={cuota.rutCuota}>
                                <td>{cuota.numero_cuota}</td>
                                <td>{cuota.monto}</td>
                                <td>{cuota.pagada}</td>
                                <td>{cuota.fechaCuota}</td>
                                <td>{cuota.fechaDePago}</td>
                                <Button
                                    className="boton"
                                    variant="success"
                                    onClick={() => pagar(cuota.id_cuota)}
                                    disabled={cuota.pagada === "Pagada"}
                                >PAGAR</Button>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default ListadoCuotasComponent;