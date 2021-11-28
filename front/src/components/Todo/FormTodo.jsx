import React, { useRef, useState, useContext } from "react";
import Store from "../utilities/Store";
import HOST_API from "./../utilities/Connection";

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

    const request = {
      name: state.name,
      id: null,
      completed: false
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
  };

  const onEdit = (event) => {
    event.preventDefault();

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
            Actualizar
          </button>
        )}
        {!item.id && (
          <button className="btn btn-success" onClick={onAdd}>
            Crear
          </button>
        )}
      </div>
    </form>
  );
};

export default FormTodo;
