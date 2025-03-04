import React, { useState } from "react";
import {
  Modal,
  Box,
  Typography,
  Button,
  IconButton,
  Link,
} from "@mui/material";
import {
  Timeline,
  TimelineItem,
  TimelineSeparator,
  TimelineConnector,
  TimelineDot,
  TimelineContent,
} from "@mui/lab";
import DirectionsBusIcon from "@mui/icons-material/DirectionsBus";
import FlightIcon from "@mui/icons-material/Flight";
import DirectionsCarIcon from "@mui/icons-material/DirectionsCar";
import SubwayIcon from "@mui/icons-material/Subway";
import { Close, Add } from "@mui/icons-material";
import IRouteData from "../types/IRouteData";
import { TransportationTypeEnum } from "../types/enum/TransportationTypeEnum";
interface routeInspectModalProps {
  selectedRoute: Array<IRouteData>;
  closeModal: () => void;
  openInspectModal: boolean;
}

// shows details of selected route
const RouteInspectModal = ({
  selectedRoute,
  closeModal,
  openInspectModal,
}: routeInspectModalProps) => {
  // close modal
  const handleClose = () => {
    closeModal();
  };

  // get icon by TransportationType
  const getTransportIcon = (type: TransportationTypeEnum) => {
    switch (type) {
      case TransportationTypeEnum.BUS:
        return (
          <DirectionsBusIcon
            fontSize="small"
            sx={{ verticalAlign: "middle", marginRight: 0.5 }}
          />
        );
      case TransportationTypeEnum.FLIGHT:
        return (
          <FlightIcon
            fontSize="small"
            sx={{ verticalAlign: "middle", marginRight: 0.5 }}
          />
        );
      case TransportationTypeEnum.UBER:
        return (
          <DirectionsCarIcon
            fontSize="small"
            sx={{ verticalAlign: "middle", marginRight: 0.5 }}
          />
        );
      case TransportationTypeEnum.SUBWAY:
        return (
          <SubwayIcon
            fontSize="small"
            sx={{ verticalAlign: "middle", marginRight: 0.5 }}
          />
        );
      default:
        return null;
    }
  };

  return (
    <Modal open={openInspectModal} onClose={handleClose}>
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
          Inspect Route
        </Typography>

        <Box
          sx={{
            padding: "16px",
            display: "flex",
            justifyContent: "flex-start",
          }}
        >
          <Timeline sx={{ alignItems: "flex-start", padding: 0 }}>
            {selectedRoute.map((step, index) => (
              <TimelineItem
                key={index}
                sx={{ "&::before": { display: "none" } }}
              >
                <TimelineSeparator>
                  <TimelineDot />
                  {step.transportationType && <TimelineConnector />}
                </TimelineSeparator>
                <TimelineContent sx={{ textAlign: "left", marginLeft: "8px" }}>
                  <Typography variant="h6" fontWeight="bold">
                    {step.location.name} ({step.location.locationCode})
                  </Typography>
                  <Typography variant="body2">
                    {getTransportIcon(
                      step.transportationType as TransportationTypeEnum
                    )}{" "}
                    {step.transportationType}
                  </Typography>
                </TimelineContent>
              </TimelineItem>
            ))}
          </Timeline>
        </Box>

        <IconButton
          onClick={handleClose}
          sx={{ position: "absolute", top: 10, right: 10 }}
        >
          <Close />
        </IconButton>

        <Box sx={{ display: "flex", justifyContent: "space-between", mt: 2 }}>
          <Button onClick={handleClose} color="secondary">
            Cancel
          </Button>
        </Box>
      </Box>
    </Modal>
  );
};

export default RouteInspectModal;
