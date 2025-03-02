import React, { useEffect, useState } from "react";
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
interface LocationUpdateModalProps {
  locationData: ILocationData;
  modelOpen: boolean;
  handleCancel: () => void;
  handleConfirm: () => void;
}
// Modal bileşenini içeren ana bileşen

const LocationUpdateModal = ({
  locationData,
  modelOpen,
  handleCancel,
  handleConfirm,
}: LocationUpdateModalProps) => {
  // Modal'ın açık olup olmadığını kontrol eden state
  const [open, setOpen] = useState(false);

  // Form alanları için state
  const [formData, setFormData] = useState<ILocationData>(locationData);
  useEffect(() => {
    setFormData(locationData);
  }, [locationData]);
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
    LocationService.update(formData)
      .then(() => {
        toast("Location Updated Successfully");
        handleConfirm();
        handleClose(); // Modal'ı kapat
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
            {locationData.id}
            Update Location
          </Typography>

          {/* Modal'ı kapatma butonu */}
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

          {/* Submit ve Cancel butonları */}
          <Box sx={{ display: "flex", justifyContent: "space-between", mt: 2 }}>
            <Button onClick={() => handleCancel()} color="secondary">
              Cancel
            </Button>
            <Button onClick={handleSubmit} variant="contained" color="primary">
              Update
            </Button>
          </Box>
        </Box>
      </Modal>
    </div>
  );
};

export default LocationUpdateModal;
