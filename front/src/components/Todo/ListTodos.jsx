import React, { useContext, useEffect } from "react";
import Store from "../utilities/Store";
import HOST_API from "../utilities/Connection";
import Swal from "sweetalert2";

const ListTodos = (props) => {

  const { dispatch, state: { todo } } = useContext(Store);
  const currentList = todo.list.filter(todo => {
    return todo.groupTodoId === props.idGroup;
  })
  

  useEffect(() => {
    fetch(HOST_API + "/todos")
      .then((response) => response.json())
      .then((list) => {
        dispatch({ type: "update-list", list });
      });
  }, [dispatch]);

  const onDelete = (id) => {

    Swal.fire({
      title: '¿Estas seguro de querer eliminar la tarea?',
      showDenyButton: true,
      confirmButtonText: 'SI',
      denyButtonText: `NO`,
    }).then((result) => {
      if (result.isConfirmed) {
        fetch(HOST_API + "/todo/" + id, {
          method: "DELETE",
        }).then((list) => {
          dispatch({ type: "delete-item", id });
        });
        Swal.fire('¡Eliminada!', '', 'success')
      }
    })
  };

  const onEdit = (todo) => {
    dispatch({ type: "edit-item", item: todo });
  };

  const onChange = (event, todo) => {
    const request = {
      name: todo.name,
      id: todo.id,
      completed: event.target.checked,
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
      });
  };

  const decorationDone = {
    textDecoration: "line-through",
  };
  return (
    <div className="container p-3">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Tarea</th>
            <th>¿Completado?</th>
          </tr>
        </thead>
        <tbody>
          {currentList.map((todo) => {
            return (
              <tr key={todo.id} style={todo.completed ? decorationDone : {}}>
                <td>{todo.id}</td>
                <td>{todo.name}</td>
                <td>
                  <input
                    type="checkbox"
                    defaultChecked={todo.completed}
                    onChange={(event) => onChange(event, todo)}
                  ></input>
                </td>
                <td>
                  <button className="btn btn-danger" onClick={() => onDelete(todo.id)}>Eliminar</button>
                </td>
                <td>
                  <button className="btn btn-primary" onClick={() => onEdit(todo)}>Editar</button>
                </td>
              </tr>
            );
          })}
        </tbody>
      </table>
    </div>
  );
};

export default ListTodos;
