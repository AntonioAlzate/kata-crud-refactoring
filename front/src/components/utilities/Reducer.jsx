function Reducer(state, action) {
  switch (action.type) {
    // Groups
    case "add-group":
      const groupUp = state.group.list;
      groupUp.push(action.item);
      return { ...state, group: { list: groupUp, item: {} } };

    case "delete-group":
      const groupUpDelete = state.group;
      const listGroupUpdate = groupUpDelete.list.filter((item) => {
        return item.idGroupTodos !== action.id;
      });
      groupUpDelete.list = listGroupUpdate;
      return { ...state, group: groupUpDelete };

      case "update-list-group":
        const todoGroupUpList = state.group;
        todoGroupUpList.list = action.list;
        return { ...state, group: todoGroupUpList };

    // Todo
    case "update-item":
      const todoUpItem = state.todo;
      const listUpdateEdit = todoUpItem.list.map((item) => {
        if (item.id === action.item.id) {
          return action.item;
        }
        return item;
      });
      todoUpItem.list = listUpdateEdit;
      todoUpItem.item = {};
      return { ...state, todo: todoUpItem };
    case "delete-item":
      const todoUpDelete = state.todo;
      const listUpdate = todoUpDelete.list.filter((item) => {
        return item.id !== action.id;
      });
      todoUpDelete.list = listUpdate;
      return { ...state, todo: todoUpDelete };
    case "update-list":
      const todoUpList = state.todo;
      todoUpList.list = action.list;
      return { ...state, todo: todoUpList };
    case "edit-item":
      const todoUpEdit = state.todo;
      todoUpEdit.item = action.item;
      return { ...state, todo: todoUpEdit };
    case "add-item":
      const todoUp = state.todo.list;
      todoUp.push(action.item);
      return { ...state, todo: { list: todoUp, item: {} } };
    default:
      return state;
  }
}

export default Reducer;
