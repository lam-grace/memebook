import Table from "../table/Table";
import {useState} from "react";
import Form from 'react-bootstrap/Form';
import {Button} from "react-bootstrap";
import {getMovieList} from "../../api/MovieApi";



 const AllMovies = ({movies}) => {
    const [searchValue, setSearchValue] = useState("");
    const [numberOfResults, setNumberOfRecords] = useState(0);
     const [isSearching, setIsSearching] = useState(false);
     const [movieList, setMovieList] = useState(movies);
    const doSetSearchValue = (event) => {
        setSearchValue(event.target.value);
    };
    const handleSearch = (event) => {
        if (event !== undefined) event.preventDefault();
        if (!isSearching) {
            setIsSearching(true);
            const search = async () => {
                getMovieList(searchValue).then(data => {
                    setMovieList(data);
                    setNumberOfRecords(data.length);
                    setIsSearching(false);
                });
            }
            search();
        }


    };

    const columns = [
        { label: "Title", accessor: "title", sortable: true, sortbyOrder: "desc" },
        { label: "Length", accessor: "lengthMinutes", sortable: true },
        { label: "Director", accessor: "directorName", sortable: true },
        { label: "Tag Line", accessor: "tagline", sortable: true }
    ];

    return (

            <Form onSubmit={doSetSearchValue}>
                <Form.Control
                    type="search"
                    placeholder="Search"
                    className="me-2"
                    aria-label="Search"
                    value={searchValue}
                    onChange={doSetSearchValue}
                />
                <Button variant="outline-success" onClick={handleSearch} type="submit">Search</Button>


                <div className="table_container">

                    <h1>Sortable Movies Table {numberOfResults}</h1>
                    <Table
                        caption="Note the column headers are sortable."
                        data={movieList}
                        columns={columns}
                    />
                </div>
            </Form>

    );
};

export default AllMovies;
