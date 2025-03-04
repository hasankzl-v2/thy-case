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
import SettingsIcon from "@mui/icons-material/Settings";
import { Link } from "react-router-dom";
import LocationOnIcon from "@mui/icons-material/LocationOn";
import DirectionsCarIcon from "@mui/icons-material/DirectionsCar";
import RouteIcon from "@mui/icons-material/Route";
const App = () => {
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
        <ListItem
          disablePadding
          sx={{
            fontFamily: "Roboto, sans-serif",
            fontWeight: 500,
            fontSize: "16px",
            letterSpacing: "0.5px",
            color: "#333",
          }}
        >
          <ListItemButton component={Link} to="/location">
            <ListItemIcon>
              <LocationOnIcon />
            </ListItemIcon>
            <ListItemText primary="Locations" />
          </ListItemButton>
        </ListItem>
        <ListItem disablePadding>
          <ListItemButton component={Link} to="/transportation">
            <ListItemIcon>
              <DirectionsCarIcon />
            </ListItemIcon>
            <ListItemText primary="Transportations" />
          </ListItemButton>
        </ListItem>
        <ListItem disablePadding>
          <ListItemButton component={Link} to="/route">
            <ListItemIcon>
              <RouteIcon />
            </ListItemIcon>
            <ListItemText primary="Routes" />
          </ListItemButton>
        </ListItem>
      </List>
    </Drawer>
  );
};

export default App;
