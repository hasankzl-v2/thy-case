import React, { useEffect, useState } from "react";
import { Modal, Box, Typography, TextField, IconButton } from "@mui/material";
import { Close } from "@mui/icons-material";
import ILocationData from "../types/ILocationData";
import LocationService from "../service/LocationService";
import { toast } from "react-toastify";
import IErrorResponse from "../types/response/IErrorResponse";
import ToastComponents from "./ToastComponents";
import { emptyLocation } from "../Constants";
import ButtonComponent from "./ButtonComponent";
interface LocationUpdateModalProps {
  locationData: ILocationData;
  modelOpen: boolean;
  handleCancel: () => void;
  handleConfirm: () => void;
}

const LocationUpdateModal = ({
  locationData,
  modelOpen,
  handleCancel,
  handleConfirm,
}: LocationUpdateModalProps) => {
  // modal show state
  const [open, setOpen] = useState(false);

  // when a data selected from props update state
  const [formData, setFormData] = useState<ILocationData>(locationData);
  useEffect(() => {
    setFormData(locationData);
  }, [locationData]);

  // close modal
  const handleClose = () => {
    setOpen(false);
    setFormData(emptyLocation);
  };
  // update form valeus
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  // send data
  const handleSubmit = () => {
    LocationService.update(formData)
      .then(() => {
        toast("Location Updated Successfully");
        handleConfirm();
        handleClose(); // close modal after successfully sent
      })
      .catch((e) => {
        const errorRequest: IErrorResponse = e.response.data;
        ToastComponents.showErrorToast(errorRequest);
      });
  };

  return (
    <div>
      <Modal open={modelOpen} onClose={() => handleCancel()}>
        <Box
          sx={{
            position: "absolute",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
            width: 400,
            bgcolor: "background.paper",
            borderRadius: 2,
            boxShadow: 24,
            p: 4,
            display: "flex",
            flexDirection: "column",
          }}
        >
          <Typography variant="h6" component="h2" gutterBottom>
            Update Location
          </Typography>

          <IconButton
            onClick={handleCancel}
            sx={{ position: "absolute", top: 10, right: 10 }}
          >
            <Close />
          </IconButton>
          <TextField
            label="Id"
            name="id"
            value={formData.id}
            disabled
            fullWidth
            margin="normal"
          />
          <TextField
            label="Name"
            name="name"
            value={formData.name}
            onChange={handleInputChange}
            fullWidth
            margin="normal"
          />
          <TextField
            label="Country"
            name="country"
            value={formData.country}
            onChange={handleInputChange}
            fullWidth
            margin="normal"
          />
          <TextField
            label="City"
            name="city"
            value={formData.city}
            onChange={handleInputChange}
            fullWidth
            margin="normal"
          />
          <TextField
            label="Location Code"
            name="locationCode"
            value={formData.locationCode}
            onChange={handleInputChange}
            fullWidth
            margin="normal"
          />
          <Box sx={{ display: "flex", justifyContent: "space-between", mt: 2 }}>
            <ButtonComponent text={"Cancel"} onClick={handleCancel} />
            <ButtonComponent text={"Update"} onClick={handleSubmit} />
          </Box>
        </Box>
      </Modal>
    </div>
  );
};

export default LocationUpdateModal;
