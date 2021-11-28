import React from "react";
import FormTodo from './components/Todo/FormTodo';
import ListTodos from './components/Todo/ListTodos';
import { StoreProvider } from './components/utilities/Store';

function App() {
  return <StoreProvider>
    <h3>To-Do List</h3>
    <FormTodo />
    <ListTodos />
  </StoreProvider>
}

export default App;
