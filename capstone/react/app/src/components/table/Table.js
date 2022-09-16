import { useSortableTable } from "./useSortableTable";
import TableBody from "./TableBody";
import TableHead from "./TableHead";

const Table = ({ caption, data, columns, editClick }) => {
    const [tableData, handleSorting] = useSortableTable(data, columns);
    return (
        <>
            <table className="table">
                <caption>{caption}</caption>
                <TableHead {...{ columns, handleSorting }} />
                <TableBody {...{ columns, tableData, editClick }} />
            </table>
        </>
    );
};

export default Table;