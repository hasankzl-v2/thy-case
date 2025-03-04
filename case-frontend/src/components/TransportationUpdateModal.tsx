import { useEffect, useState } from "react";
import { Modal, Box, Typography, IconButton, TextField } from "@mui/material";
import { Close } from "@mui/icons-material";
import ILocationData from "../types/ILocationData";
import { toast } from "react-toastify";
import IErrorResponse from "../types/response/IErrorResponse";
import ToastComponents from "./ToastComponents";
import { emptyLocation, emptyUpdateTransportation } from "../Constants";
import TransportationService from "../service/TransportationService";
import SearchableLocationSelect from "./SearchableLocationSelect";
import TransportationTypeSelect from "./TransportationTypeSelect";
import MultiSelectDays from "./MulitSelectDays";
import ITransportationData from "../types/ITransportationData";
import IUpdateTransportationRequest from "../types/request/IUpdateTransportationRequest";
import ButtonComponent from "./ButtonComponent";
interface transportationUpdateModalProps {
  selectedTransportation: ITransportationData;
  modelOpen: boolean;
  handleCancel: () => void;
  handleUpdate: () => void;
}
// update modal for transportation , id should be set in data
const TransportationUpdateModal = ({
  modelOpen,
  handleUpdate,
  handleCancel,
  selectedTransportation,
}: transportationUpdateModalProps) => {
  const [selectedDest, setSelectedDest] =
    useState<ILocationData>(emptyLocation);
  const [selectedOrigin, setSelectedOrigin] =
    useState<ILocationData>(emptyLocation);
  const [formData, setFormData] = useState<IUpdateTransportationRequest>(
    emptyUpdateTransportation
  );

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
  // close modal
  const handleClose = () => {
    handleCancel();
    clearForm();
  };

  // validate data before send
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

  // submit update request
  const handleSubmit = () => {
    if (checkBeforeSubmit()) {
      TransportationService.update(formData)
        .then(() => {
          toast(" Transportation Updated Successfully");
          clearForm();
          handleUpdate();
          handleClose();
        })
        .catch((e) => {
          const errorRequest: IErrorResponse = e.response.data;
          ToastComponents.showErrorToast(errorRequest);
        });
    }
  };

  // select origin of transportation
  const handleSelectOrigin = (location: ILocationData | null) => {
    if (selectedDest.locationCode == location?.locationCode) {
      toast.error("origin and destination should not be same! ");
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

  // select destination of transportation
  const handleSelectDest = (location: ILocationData | null) => {
    if (selectedOrigin.locationCode == location?.locationCode) {
      toast.error("origin and destination should not be same! ");
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
            <ButtonComponent text={"Cancel"} onClick={handleClose} />
            <ButtonComponent text={"Update"} onClick={handleSubmit} />
          </Box>
        </Box>
      </Modal>
    </div>
  );
};

export default TransportationUpdateModal;
