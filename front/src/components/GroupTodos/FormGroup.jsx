import React, { useRef, useState, useContext } from "react";
import Swal from "sweetalert2";
import Store from "../utilities/Store";
import HOST_API from "./../utilities/Connection";

const FormGroup = () => {
  const formRef = useRef(null);
  const {
    dispatch,
    state: { group },
  } = useContext(Store);
  const item = group.item;
  const [state, setState] = useState(item);

  const onAdd = (event) => {
    event.preventDefault();

    if(state.name === "" || state.name === null || state.name === undefined){
      Swal.fire({
        icon: 'error',
        title: 'El nombre de un grupo no puede ser vacío',
        text: 'Recuerda ingresar un nombre para el grupo de tareas antes de dar click en "Agregar Grupo"',
      })
      return;
    }

    const request = {
      name: state.name,
      id: null,
    };

    fetch(HOST_API + "/grouptodos", {
      method: "POST",
      body: JSON.stringify(request),
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .then((group) => {
        dispatch({ type: "add-group", item: group });
        setState({ name: "" });
        formRef.current.reset();
      }).catch(error => console.log(error.message));

      Swal.fire(
        '¡Grupo creado exitosamente!',
        'El grupo ' + state.name + ' se ha creado con exito.',
        'success'
      )
  };

  return (
    <form ref={formRef}>
      <div className="container input-group mt-3">
        <input
          type="text"
          name="name"
          className="form-control"
          placeholder="Ingresa el nombre del grupo de tareas"
          defaultValue={item.name}
          onChange={(event) => {
            setState({ ...state, name: event.target.value });
          }}
        ></input>
        <button className="btn btn-success" onClick={onAdd}>
          Agregar Grupo
        </button>
      </div>
    </form>
  );
};

export default FormGroup;
