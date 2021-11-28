import React, { useContext, useEffect } from "react";
import Store from "../utilities/Store";
import HOST_API from "../utilities/Connection";
import ListTodos from "../Todo/ListTodos";
import FormTodo from "../Todo/FormTodo";
import Swal from "sweetalert2";

const ListGroups = () => {
  const {
    dispatch,
    state: { group },
  } = useContext(Store);
  const currentList = group.list;

  useEffect(() => {
    fetch(HOST_API + "/groups")
      .then((response) => response.json())
      .then((list) => {
        dispatch({ type: "update-list-group", list });
      });
  }, [dispatch]);

  const onDelete = (id) => {

    Swal.fire({
      title: '¿Estas seguro de eliminar el grupo?',
      showDenyButton: true,
      confirmButtonText: 'SI',
      denyButtonText: `NO`,
    }).then((result) => {
      if (result.isConfirmed) {
        fetch(HOST_API + "/grouptodos/" + id, {
          method: "DELETE",
        }).then((list) => {
          dispatch({ type: "delete-group", id });
        });
        Swal.fire('¡Eliminado!', '', 'success')
      }
    })
    
  };

  return (
    <div className="text-center container input-group">
      {currentList.map((group) => {
        return (
          <div key={group.idGroupTodos} className="container text-center mt-3 p-3">
            <fieldset className="border">
              <legend className="w-auto">
                <b>Grupo: </b> {group.name.toUpperCase()}
                <button className="btn btn-danger m-4" onClick={() => onDelete(group.idGroupTodos)}>
                  Eliminar
                </button>
              </legend>
              {<FormTodo idGroup={group.idGroupTodos} />}
              {<ListTodos idGroup={group.idGroupTodos} />}
            </fieldset>
          </div>
        );
      })}
    </div>
  );
};

export default ListGroups;
