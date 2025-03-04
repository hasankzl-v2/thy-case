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
              <SettingsIcon />
            </ListItemIcon>
            <ListItemText primary="Locations" />
          </ListItemButton>
        </ListItem>
        <ListItem disablePadding>
          <ListItemButton component={Link} to="/transportation">
            <ListItemIcon>
              <SettingsIcon />
            </ListItemIcon>
            <ListItemText primary="Transportations" />
          </ListItemButton>
        </ListItem>
        <ListItem disablePadding>
          <ListItemButton component={Link} to="/route">
            <ListItemIcon>
              <SettingsIcon />
            </ListItemIcon>
            <ListItemText primary="Routes" />
          </ListItemButton>
        </ListItem>
      </List>
    </Drawer>
  );
};

export default App;
