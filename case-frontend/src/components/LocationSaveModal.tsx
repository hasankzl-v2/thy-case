import React, { useState } from "react";
import {
  Modal,
  Box,
  Typography,
  TextField,
  Button,
  IconButton,
} from "@mui/material";
import { Close, Add } from "@mui/icons-material";
import ILocationData from "../types/ILocationData";
import LocationService from "../service/LocationService";
import { toast } from "react-toastify";
import IErrorResponse from "../types/response/IErrorResponse";
import ToastComponents from "./ToastComponents";
import { emptyLocation } from "../Constants";
import ButtonComponent from "./ButtonComponent";
interface LocationModalProps {
  handleSave: () => void;
}
const LocationModal = ({ handleSave }: LocationModalProps) => {
  const [open, setOpen] = useState(false);

  // state for form data
  const [formData, setFormData] = useState<ILocationData>(emptyLocation);

  // open modal with empty form
  const handleOpen = () => {
    setOpen(true);
    setFormData(emptyLocation);
  };

  // close modal
  const handleClose = () => {
    setOpen(false);
    setFormData(emptyLocation);
  };
  // update form input
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  // send data
  const handleSubmit = () => {
    LocationService.create(formData)
      .then(() => {
        toast("New Location Added Successfully");
        handleSave();
        handleClose(); // Modal'ı kapat
      })
      .catch((e) => {
        const errorRequest: IErrorResponse = e.response.data;
        ToastComponents.showErrorToast(errorRequest);
      });
  };

  return (
    <div>
      <Button
        startIcon={<Add />}
        sx={{
          backgroundColor: "white", //
          color: "#e60012", // thy red
          "&:hover": {
            backgroundColor: "#fff4f4", // hover red
          },
        }}
        onClick={handleOpen}
      >
        Add New Location
      </Button>

      <Modal open={open} onClose={handleClose}>
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
            Location Details
          </Typography>

          <IconButton
            onClick={handleClose}
            sx={{ position: "absolute", top: 10, right: 10 }}
          >
            <Close />
          </IconButton>

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
            <ButtonComponent text={"Cancel"} onClick={handleClose} />
            <ButtonComponent text={"Submit"} onClick={handleSubmit} />
          </Box>
        </Box>
      </Modal>
    </div>
  );
};

export default LocationModal;
