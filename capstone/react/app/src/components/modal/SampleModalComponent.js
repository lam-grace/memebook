import React, { useState } from 'react';
import { Button } from 'react-bootstrap';
import ModalComponent from './ModalComponent';

const SampleModalComponent = () => {

  const [modalState, setModalState] = useState({
    show: false,
    title: '',
    body: '',
    data: []
  });

  const handleShow = () => {

    const myObject = [
      {
        id: 1,
        name: 'Victor Rippin',
        address: '4032 Cordia Streets'
      }, {
        id: 2,
        name: 'Jamey Zieme',
        address: '3733 Tremblay Throughway'
      }, {
        id: 3,
        name: 'Madelyn Ruecker Sr.',
        address: '44487 Reba Drive'
      },
    ];

    setModalState({
      show: true,
      title: 'Group People',
      body: 'Hi, find my group details',
      data: myObject
    });
  };

  const handleClose = (fromModal) => {
    alert(fromModal.msg);

    setModalState({
      show: false
    });
  };

  return (
    <div>
      <Button variant="primary" onClick={handleShow} >
        Launch Bootstrap Modal
      </Button>

      <ModalComponent
        show={modalState.show}
        title={modalState.title}
        body={modalState.body}
        data={modalState.data}
        onClick={handleClose}
        onHide={handleClose} />

    </div>
  );
};

export default SampleModalComponent;