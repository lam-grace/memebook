import React, { useEffect, useState } from 'react';
import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import {useParams} from "react-router-dom";
import {getUserDetail} from "../../api/UserApi";
import {approveMeme, deleteMeme, getUnapprovedMemes} from "../../api/MemeApi";
import {LinkContainer} from "react-router-bootstrap";
import Nav from "react-bootstrap/Nav";
import {Button} from "react-bootstrap";
import Fade from 'react-reveal/Fade';
import MemeComponent from "../meme/MemeComponent";

const ApproveMemes = () => {
    let { userId } = useParams();
    const [memeList, setMemeList] = useState([]);

    useEffect(() => {
        const unapprovedMemes = async () => {
            const data = await getUnapprovedMemes();
            setMemeList(data);
        }
        unapprovedMemes();
    }, []);

    // const getDetail = () => {
    //     const userDetail = async () => {
    //         if (!callingDetail) {
    //             setCalling(true);
    //             getUserDetail(userId).then(data => {
    //                 setUser(data);
    //             });
    //         }
    //     }
    //     userDetail();
    // };
    // getDetail();

    const handleDelete = (id) => {
        const cancel = async () => {
            deleteMeme(id).then(deleted => {
                getUnapprovedMemes().then(data => {
                    setMemeList(data);
                });
            });
        }
        cancel();
    };

    useEffect(() => {setMemeList(memeList);}, [memeList]);

    const handleApprove = (id) => {
        const approve = async () => {
            approveMeme(id).then(approved => {
                getUnapprovedMemes().then(data => {
                    setMemeList(data);
                });
            });
        }
        approve();
    };

    function displayContent() {
        if (memeList.length == 0) {
            return <h2> There's no meme to be approved</h2>
        }
        else {
            return (
                <Row className="p-3">
                    {memeList?.map(meme => (
                        <Col key={meme.memeId} md="auto">
                            <Fade clear>
                            <Card className="my-2 float-start"
                                  style={{
                                      width: '470px'
                                  }}>
                                <Card.Body>
                                <Card.Link>
                                    <LinkContainer to={{pathname: `/memeDetail/${meme.memeId}`}} >
                                        <Nav.Link><MemeComponent memeId={meme.memeId} cssClass="meme" altText="image"/></Nav.Link>
                                    </LinkContainer>
                                </Card.Link>

                                    <div>
                                <span className="float-start mt-2 mx-3"><Button variant="success" onClick={() => handleApprove(meme.memeId)} type="submit">
                                    Approve
                                </Button> </span>
                                <span className="float-end mt-2 mx-3"><Button variant="danger" onClick={() => handleDelete(meme.memeId)} type="submit">
                                    Delete
                                </Button> </span>
                                    </div>
                                </Card.Body>
                            </Card>
                            </Fade>
                        </Col>
                    ))}
                </Row>);
        }
    }

    return (
        <div className="mx-3">
            <Row>
                <div>
                    <h1>
                        Admin Approval Page
                    </h1>
                </div>
            </Row>
            {displayContent()}
        </div>

    );
}
export default ApproveMemes;