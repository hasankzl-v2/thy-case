import React, { useState } from "react";
import {
  AppBar,
  Toolbar,
  IconButton,
  Typography,
  Drawer,
  List,
  ListItem,
  ListItemText,
  Box,
  useMediaQuery,
  useTheme,
  ListItemButton,
  ListItemIcon,
} from "@mui/material";
import { Link } from "react-router-dom";
import LocationOnIcon from "@mui/icons-material/LocationOn";
import DirectionsCarIcon from "@mui/icons-material/DirectionsCar";
import RouteIcon from "@mui/icons-material/AltRoute";

// menu items
const listData = [
  {
    to: "/location",
    text: "Locations",
    icon: <LocationOnIcon />,
  },
  {
    to: "/transportation",
    text: "Transportations",
    icon: <DirectionsCarIcon />,
  },
  {
    to: "/route",
    text: "Routes",
    icon: <RouteIcon />,
  },
];

const SideBarComponent = () => {
  return (
    <Drawer
      open
      variant="permanent"
      anchor="left"
      sx={{
        "& .MuiDrawer-paper": {
          boxSizing: "border-box",
          position: "relative", // Sabit kalacak
        },
      }}
    >
      <List>
        {listData.map((data) => {
          return (
            <ListItem disablePadding>
              <ListItemButton component={Link} to={data.to}>
                <ListItemIcon>{data.icon}</ListItemIcon>
                <ListItemText primary={data.text} />
              </ListItemButton>
            </ListItem>
          );
        })}
      </List>
    </Drawer>
  );
};

export default SideBarComponent;
