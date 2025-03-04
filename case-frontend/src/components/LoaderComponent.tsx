import { useLoader } from "../context/LoaderContext";
import logo from "../assets/logo.png";
import { Box } from "@mui/material";

// loadar component used for showing loader after every axios request until request finish
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
