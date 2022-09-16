import {Button} from "reactstrap";

const TableBody = ({ columns, tableData,editClick }) => {
    return (
     <tbody>
      {tableData.map((data) => {
       return (
        <tr key={data.id}>
         {/*{columns.map(=> {*/}
         {/* const tData = data[accessor] ? data[accessor] : "——";*/}
         {/* return <td key={accessor}>{tData}</td>;*/}
         {columns.map(column => {
           const tData = data[column.accessor] ? data[column.accessor] : "——";
           if (column.callback !== undefined) {
           return <td key={column.accessor}><Button id={data[column.accessor]} onClick={editClick}>{column.label}</Button> </td>;
           } else {
           return <td key={column.accessor}>{tData}</td>;
           }
         })}
        </tr>
       );
      })}
     </tbody>
    );
   };
   
   export default TableBody;