import { useState } from "react";
import { Modal, Box, Typography, Button, IconButton } from "@mui/material";
import { Close, Add } from "@mui/icons-material";
import ILocationData from "../types/ILocationData";
import { toast } from "react-toastify";
import IErrorResponse from "../types/response/IErrorResponse";
import ToastComponents from "./ToastComponents";
import { emptyLocation, emptySaveTransportation } from "../Constants";
import TransportationService from "../service/TransportationService";
import ISaveTransportationRequest from "../types/request/ISaveTransportationRequest";
import SearchableLocationSelect from "./SearchableLocationSelect";
import TransportationTypeSelect from "./TransportationTypeSelect";
import MultiSelectDays from "./MulitSelectDays";
import ButtonComponent from "./ButtonComponent";
interface transportationSaveModalProps {
  handleSave: () => void;
}
const TransportationSaveModal = ({
  handleSave,
}: transportationSaveModalProps) => {
  // modal open state
  const [open, setOpen] = useState(false);

  const [selectedDest, setSelectedDest] =
    useState<ILocationData>(emptyLocation);
  const [selectedOrigin, setSelectedOrigin] =
    useState<ILocationData>(emptyLocation);
  const [formData, setFormData] = useState<ISaveTransportationRequest>(
    emptySaveTransportation
  );

  // open modal with empty form data
  const handleOpen = () => {
    setOpen(true);
    setFormData(emptySaveTransportation);
  };

  // close modal with empty form
  const handleClose = () => {
    setOpen(false);
    clearForm();
  };

  // validate data before submit, backend validation also added
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

  // send save request if data is valid
  const handleSubmit = () => {
    if (checkBeforeSubmit()) {
      TransportationService.create(formData)
        .then(() => {
          toast("New Transportation Added Successfully");
          clearForm();
          handleSave();
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
    // set id if location is not null
    setFormData({
      ...formData,
      originLocationId: id,
    });

    if (location) {
      setSelectedOrigin(location);
    }
  };
  // clear form data
  const clearForm = () => {
    setSelectedDest(emptyLocation);
    setSelectedOrigin(emptyLocation);
    setFormData(emptySaveTransportation);
  };

  // select destination of transportation
  const handleSelectDest = (location: ILocationData | null) => {
    if (selectedOrigin.locationCode == location?.locationCode) {
      toast.error("origin and destination should not be same ! ");
      return;
    }
    const id = location != null ? location.id : null;
    // set id if location is not null
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
      <Button
        startIcon={<Add />}
        onClick={handleOpen}
        sx={{
          backgroundColor: "white",
          color: "#e60012",
          "&:hover": {
            backgroundColor: "#fff4f4",
          },
        }}
      >
        Add New Transportation
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
            Save Transportation
          </Typography>

          <IconButton
            onClick={handleClose}
            sx={{ position: "absolute", top: 10, right: 10 }}
          >
            <Close />
          </IconButton>
          <Box m={2}>
            <SearchableLocationSelect
              label="From"
              handleSelect={handleSelectOrigin}
              selectedData={selectedOrigin.locationCode}
            />
          </Box>

          <Box m={2}>
            <SearchableLocationSelect
              label="to"
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
            <ButtonComponent text={"Save"} onClick={handleSubmit} />
          </Box>
        </Box>
      </Modal>
    </div>
  );
};

export default TransportationSaveModal;
