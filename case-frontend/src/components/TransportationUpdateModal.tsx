import React, { useEffect, useState } from "react";
import {
  Modal,
  Box,
  Typography,
  Button,
  IconButton,
  TextField,
} from "@mui/material";
import { Close, Add } from "@mui/icons-material";
import ILocationData from "../types/ILocationData";
import { toast } from "react-toastify";
import IErrorResponse from "../types/response/IErrorResponse";
import ToastComponents from "./ToastComponents";
import {
  emptyLocation,
  emptySaveTransportation,
  emptyUpdateTransportation,
} from "../Constants";
import TransportationService from "../service/TransportationService";
import ISaveTransportationRequest from "../types/request/ISaveTransportationRequest";
import SearchableLocationSelect from "./SearchableLocationSelect";
import TransportationTypeSelect from "./TransportationTypeSelect";
import MultiSelectDays from "./MulitSelectDays";
import ITransportationData from "../types/ITransportationData";
import IUpdateTransportationRequest from "../types/request/IUpdateTransportationRequest";
interface transportationUpdateModalProps {
  selectedTransportation: ITransportationData;
  modelOpen: boolean;
  handleCancel: () => void;
  handleUpdate: () => void;
}
const TransportationUpdateModal = ({
  modelOpen,
  handleUpdate,
  handleCancel,
  selectedTransportation,
}: transportationUpdateModalProps) => {
  // Modal'ın açık olup olmadığını kontrol eden state
  const [open, setOpen] = useState(false);

  const [selectedDest, setSelectedDest] =
    useState<ILocationData>(emptyLocation);
  const [selectedOrigin, setSelectedOrigin] =
    useState<ILocationData>(emptyLocation);
  const [formData, setFormData] = useState<IUpdateTransportationRequest>(
    emptyUpdateTransportation
  );

  // Modal'ı açma
  const handleOpen = () => {
    setOpen(true);
    setFormData(emptyUpdateTransportation);
  };
  useEffect(() => {
    const data = emptyUpdateTransportation;
    if (selectedTransportation.destinationLocation) {
      data.destinationLocationId =
        selectedTransportation.destinationLocation.id;
      setSelectedDest(selectedTransportation.destinationLocation);
    }

    if (selectedTransportation.originLocation) {
      data.originLocationId = selectedTransportation.originLocation.id;
      setSelectedOrigin(selectedTransportation.originLocation);
    }

    data.operationDays = selectedTransportation.operationDays;
    data.transportationType = selectedTransportation.transportationType;
    data.id = selectedTransportation.id;
    setFormData(data);
  }, [selectedTransportation]);
  // Modal'ı kapama
  const handleClose = () => {
    handleCancel();
    clearForm();
  };
  const checkBeforeSubmit = () => {
    if (formData.operationDays == null || formData.operationDays.length == 0) {
      toast.error("operatation days should be selected");
      return false;
    }
    if (formData.destinationLocationId == null) {
      toast.error("destination  should be selected");
      return false;
    }
    if (formData.originLocationId == null) {
      toast.error("origin should be selected");
      return false;
    }
    if (formData.transportationType == null) {
      toast.error(" transportation type should be selected");
      return false;
    }

    return true;
  };
  const handleSubmit = () => {
    console.log("Form submitted:", formData);
    if (checkBeforeSubmit()) {
      TransportationService.update(formData)
        .then(() => {
          toast(" Transportation Updated Successfully");
          clearForm();
          handleUpdate();
          handleClose(); // Modal'ı kapat
        })
        .catch((e) => {
          const errorRequest: IErrorResponse = e.response.data;
          ToastComponents.showErrorToast(errorRequest);
        });
    }
  };
  const handleSelectOrigin = (location: ILocationData | null) => {
    if (selectedDest.locationCode == location?.locationCode) {
      toast.error("origin and destination should not be same ! ");
      return;
    }
    const id = location != null ? location.id : null;
    setFormData({
      ...formData,
      originLocationId: id,
    });

    if (location) {
      setSelectedOrigin(location);
    }
  };
  const clearForm = () => {
    setSelectedDest(emptyLocation);
    setSelectedOrigin(emptyLocation);
    setFormData(emptyUpdateTransportation);
  };
  const handleSelectDest = (location: ILocationData | null) => {
    if (selectedOrigin.locationCode == location?.locationCode) {
      toast.error("origin and destination should not be same ! ");
      return;
    }
    const id = location != null ? location.id : null;
    setFormData({
      ...formData,
      destinationLocationId: id,
    });

    if (location) {
      setSelectedDest(location);
    }
  };

  const selectTransferType = (transportationType: any) => {
    setFormData({
      ...formData,
      transportationType,
    });
  };

  const selectOperationDays = (operationDays: any) => {
    setFormData({
      ...formData,
      operationDays,
    });
  };
  return (
    <div>
      <Modal open={modelOpen} onClose={handleClose}>
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
            Update Transportation
          </Typography>

          {/* Modal'ı kapatma butonu */}
          <IconButton
            onClick={handleClose}
            sx={{ position: "absolute", top: 10, right: 10 }}
          >
            <Close />
          </IconButton>
          <Box m={2}>
            <TextField
              label="Id"
              name="id"
              value={formData.id}
              disabled
              fullWidth
              margin="normal"
            />
          </Box>
          <Box m={2}>
            <SearchableLocationSelect
              label="origin"
              handleSelect={handleSelectOrigin}
              selectedData={selectedOrigin.locationCode}
            />
          </Box>

          <Box m={2}>
            <SearchableLocationSelect
              label="destination"
              handleSelect={handleSelectDest}
              selectedData={selectedDest.locationCode}
            />
          </Box>

          <Box m={2}>
            <TransportationTypeSelect
              handleSelect={selectTransferType}
              selectedType={formData.transportationType}
            />
          </Box>
          <Box m={2}>
            <MultiSelectDays
              handleSelect={selectOperationDays}
              selectedOperationDays={formData.operationDays}
            />
          </Box>
          <Box sx={{ display: "flex", justifyContent: "space-between", mt: 2 }}>
            <Button onClick={handleClose} color="secondary">
              Cancel
            </Button>
            <Button onClick={handleSubmit} variant="contained" color="primary">
              Save
            </Button>
          </Box>
        </Box>
      </Modal>
    </div>
  );
};

export default TransportationUpdateModal;
