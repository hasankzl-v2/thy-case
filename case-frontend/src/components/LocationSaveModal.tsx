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
interface LocationModalProps {
  handleSave: () => void;
}
const LocationModal = ({ handleSave }: LocationModalProps) => {
  // Modal'ın açık olup olmadığını kontrol eden state
  const [open, setOpen] = useState(false);

  // Form alanları için state
  const [formData, setFormData] = useState<ILocationData>(emptyLocation);

  // Modal'ı açma
  const handleOpen = () => {
    setOpen(true);
    setFormData(emptyLocation);
  };

  // Modal'ı kapama
  const handleClose = () => {
    setOpen(false);
    setFormData(emptyLocation);
  };
  // Form verilerini güncelleme
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  // Formu gönderme işlemi
  const handleSubmit = () => {
    // Veriyi işleme (örneğin API'ye gönderme veya başka bir işlem)
    console.log("Form submitted:", formData);
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
        variant="contained"
        color="success"
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

          {/* Modal'ı kapatma butonu */}
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

          {/* Submit ve Cancel butonları */}
          <Box sx={{ display: "flex", justifyContent: "space-between", mt: 2 }}>
            <Button onClick={handleClose} color="secondary">
              Cancel
            </Button>
            <Button onClick={handleSubmit} variant="contained" color="primary">
              Submit
            </Button>
          </Box>
        </Box>
      </Modal>
    </div>
  );
};

export default LocationModal;
