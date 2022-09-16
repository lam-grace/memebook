import React, {useEffect, useState} from 'react';
import Card from 'react-bootstrap/Card';
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import {useParams} from "react-router-dom";
import {ListGroup, ListGroupItem} from "reactstrap";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import {Button, ButtonGroup} from "react-bootstrap";
import {Routes, Route, useNavigate} from 'react-router-dom';
import * as Icon from 'react-bootstrap-icons';
import {likeMeme, deleteMeme, reportMeme, unlikeMeme} from "../../api/MemeApi";
import { saveAs } from 'file-saver'


import Moment from 'moment'
import {getMeme, getMemeImage} from "../../api/MemeApi";
import ImageComponent from "./ImageComponent";
import MemeComponent from "./MemeComponent";
import {deleteImage} from "../../api/ImageApi";
import {getUser} from "../../api/UserApi";
import HeadShake from 'react-reveal/HeadShake';



const MemeDetail = () => {
    let { memeId } = useParams();
    const [memeDetail, setMemeDetail] = useState({});
    const [callingDetail, setCalling] = useState(false);
    const navigate = useNavigate();
    const [like, setLike] = useState([]);

    const [user, setUser] = useState('');
    useEffect(() => {
        const getAuthor = async () => {
            const data = await getUser();
            setUser(data);
        }
        getAuthor();
    }, []);


    useEffect(() => {
        const memeDetail = async () => {
            if (!callingDetail) {
                setCalling(true);
                getMeme(memeId).then(data => {     // getMeme() will have all the infomation
                    setMemeDetail(data);
                });
            }
        }
        memeDetail();
    }, []);

    useEffect(() => {
        setMemeDetail(memeDetail);
    }, [memeDetail]);


    const navigateUseTemplate = () => {
        // 👇️ navigate to /
        navigate({pathname: `/createMeme/${memeDetail?.imageId}`});    //MakeMeme with meme id
    };

    const navigateUserProfile = () => {
        // 👇️ navigate to /
        navigate({pathname: `/viewProfile/${memeDetail?.authorId}`});  //  add specific users using their id
    };

    const handleLike = (event) => {
        likeMeme(memeId).then(meme => {
            setMemeDetail(meme);
            // window.location.reload(false)
        });
    }

    const handleUnlike = (event) => {
        unlikeMeme(memeId).then(meme => {
            setMemeDetail(meme);
            // window.location.reload(false)
        });
    }

    const handleReport = (event) => {
        const report = async () => {
            await reportMeme(memeId).then(data => {
                navigate(-1);
            });
        }
        report();
    }

    const handleDownload = (event) => {
        const downloadImage = async () => {
            let item = document.getElementsByClassName("bigMeme")[0];
            saveAs(item.getAttribute("src"), "meme" + `${memeDetail.memeId}` + ".jpg") // Put your image url here.
        }
        downloadImage();
    }

    const handleDelete = () => {
        const cancel = async () => {
            await deleteMeme(memeId).then(data => {
                navigate(-1);
            });
        }
        cancel();
    };


    const columns = [
        { label: "Name", accessor: "name", sortable: true, sortbyOrder: "asc" }
    ];
    return (
        <>
            <Row>
                <div>
                    <h1 className="p-2 header2">
                        Meme Detail:
                    </h1>
                </div>
            </Row>
            <Row className="p-2">
                <Col key="imageCard">
                    <Card className="my-2 p-2"
                          style={{
                              minWidth: '16rem',
                              maxHeight: '50rem'
                          }}>
                        <MemeComponent memeId={memeId} cssClass="bigMeme" altText="image"/>
                        <div className='text-center pt-2'>
                            {memeDetail.favorite === true ?
                                <Button variant="primary" className='float-start mx-5' onClick={handleUnlike}>
                                    Unlike<Icon.HandThumbsDown className="ms-1 mb-1"/></Button> :
                                <Button variant="outline-primary" className='float-start mx-5' onClick={handleLike}>
                                    Like<Icon.HandThumbsUp className="ms-1 mb-1"/></Button>
                            }

                            {' '}<Button variant="outline-warning" className='float-none' onClick={handleDownload}>Download</Button>{' '}
                            <Button variant="outline-danger" className='float-end mx-5' onClick={handleReport}>Report</Button>{' '}
                        </div>
                    </Card>
                </Col>

                <Col key="titleCard">
                    <Card className="my-2 p-2"
                          style={{
                              minWidth: '16rem'
                          }}>
                        <Card.Body>
                            <HeadShake>
                            <Card.Link onClick={navigateUserProfile} className="p-2" /*go to user view memes page */><Icon.PersonFill className="icon-colour"/>
                                User: {memeDetail?.author} </Card.Link>
                            </HeadShake>
                            <HeadShake>
                            <Card.Text className="p-2">
                                <Icon.CalendarHeartFill className="icon-colour"/>
                                Meme upload date: {memeDetail?.uploadDate}
                            </Card.Text>
                            </HeadShake>

                            <HeadShake spy={memeDetail?.popularity}>
                            <Card.Text className="p-2">
                                <Icon.HandThumbsUpFill className="icon-colour"/>
                                Likes: {memeDetail?.popularity}
                            </Card.Text>
                            </HeadShake>
                            {/*<Card.Text>*/}
                            {/*    <Icon.TagsFill className="icon-colour"/>*/}
                            {/*    Tags:*/}
                            {/*</Card.Text>*/}

                            <Button variant="outline-secondary" type="submit" onClick={navigateUseTemplate} className="mt-2">Use Template</Button>{' '}

                            {(user.role === window.ADMIN || user.userId === memeDetail.authorId) &&
                            <div className='text-left'>
                                <Button variant="outline-danger" onClick={handleDelete} type={"submit"} className="mt-2"> Delete </Button>
                            </div>
                            }
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
            {/*<Modal show={showMovieDetailModal} onHide={handleCancel}>*/}
            {/*    <Form onSubmit={handleUpdateDetail}>*/}
            {/*        <Modal.Header closeButton>*/}
            {/*            <Modal.Title>Edit Movie Overview</Modal.Title>*/}
            {/*        </Modal.Header>*/}
            {/*        <Modal.Body>*/}
            {/*            <Form.Group className="mb-3">*/}
            {/*                <Form.Label>Description</Form.Label>*/}
            {/*                <Form.Control as="textarea" rows={10}*/}
            {/*                              value={movieOverview} onChange={doSetOverview} />*/}
            {/*            </Form.Group>*/}
            {/*        </Modal.Body>*/}
            {/*        <Modal.Footer>*/}
            {/*            <ButtonGroup>*/}
            {/*                <Button variant="primary" onClick={handleUpdateDetail} type="submit">*/}
            {/*                    Save*/}
            {/*                </Button>*/}
            {/*                <Button variant="secondary" onClick={handleCancel}>*/}
            {/*                    Cancel*/}
            {/*                </Button>*/}
            {/*            </ButtonGroup>*/}
            {/*        </Modal.Footer>*/}
            {/*    </Form>*/}
            {/*</Modal>*/}
        </>
    );
}
export default MemeDetail;