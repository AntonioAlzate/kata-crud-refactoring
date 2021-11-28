import React, { useRef, useState, useContext } from "react";
import Store from "../utilities/Store";
import HOST_API from "./../utilities/Connection";
import Swal from "sweetalert2";

const FormTodo = (props) => {
  const formRef = useRef(null);
  const {
    dispatch,
    state: { todo },
  } = useContext(Store);
  const item = todo.item;
  const [state, setState] = useState(item);

  const onAdd = (event) => {
    event.preventDefault();

    if (state.name === "" || state.name === null || state.name === undefined) {
      Swal.fire({
        icon: "error",
        title: "El nombre de una Tarea no puede estar vacio",
        text: 'Recuerda ingresar un nombre para la tarea antes de dar click en "Crear Tarea"',
      });
      return;
    }

    const request = {
      name: state.name,
      id: null,
      completed: false,
    };

    fetch(HOST_API + "/todo/" + props.idGroup, {
      method: "POST",
      body: JSON.stringify(request),
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .then((todo) => {
        dispatch({ type: "add-item", item: todo });
        setState({ name: "" });
        formRef.current.reset();
      });

      Swal.fire(
        '¡Tarea creada exitosamente!',
        'La tarea ' + state.name + ' se ha creado con exito.',
        'success'
      )
  };

  const onEdit = (event) => {
    event.preventDefault();

    if (state.name === "" || state.name === null || state.name === undefined) {
      Swal.fire({
        icon: "error",
        title: "El nombre de una Tarea no puede estar vacio",
        text:
          "Recuerda ingresar un nombre para la tarea que deseas actualizar " +
          'antes de dar click en "Actualizar Tarea"',
      });
      return;
    }

    const request = {
      name: state.name,
      id: item.id,
      isCompleted: item.isCompleted,
    };

    fetch(HOST_API + "/todo/" + props.idGroup, {
      method: "PUT",
      body: JSON.stringify(request),
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .then((todo) => {
        dispatch({ type: "update-item", item: todo });
        setState({ name: "" });
        formRef.current.reset();
      });

      Swal.fire(
        '¡Tarea Actualizada exitosamente!',
        'La tarea ' + state.name + ' se ha Actualizado con exito.',
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
          placeholder="¿Qué piensas hacer hoy?"
          defaultValue={item.name}
          onChange={(event) => {
            setState({ ...state, name: event.target.value });
          }}
        ></input>
        {item.id && (
          <button className="btn btn-primary" onClick={onEdit}>
            Actualizar Tarea
          </button>
        )}
        {!item.id && (
          <button className="btn btn-success" onClick={onAdd}>
            Crear Tarea
          </button>
        )}
      </div>
    </form>
  );
};

export default FormTodo;
