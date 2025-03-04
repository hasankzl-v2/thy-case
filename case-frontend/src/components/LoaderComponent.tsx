import React from "react";
import { CircularProgress, Box } from "@mui/material";
import { useLoader } from "../context/LoaderContext";
import logo from "../assets/logo.png";
const Loader = () => {
  const { loading } = useLoader();

  if (!loading) return <></>;

  return (
    <Box
      sx={{
        position: "fixed",
        top: 0,
        left: 0,
        width: "100vw",
        height: "100vh",

        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        zIndex: 9999,
      }}
    >
      <img
        src={logo}
        alt="Custom Icon"
        style={{ width: "80px", height: "80px" }}
        className="rotating-logo"
      />
    </Box>
  );
};

export default Loader;
