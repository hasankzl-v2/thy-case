import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";

import { Link } from "react-router-dom";
import thyIcon from "../assets/thy-icon.png";
function ResponsiveAppBar() {
  return (
    <AppBar position="static" color="transparent" variant="outlined">
      <Toolbar disableGutters>
        <IconButton
          color="inherit"
          component={Link}
          to="/"
          style={{ borderRadius: "1px" }}
        >
          <img
            src={thyIcon}
            alt="Custom Icon"
            style={{ width: "160px", height: "80px" }}
          />
        </IconButton>
      </Toolbar>
    </AppBar>
  );
}
export default ResponsiveAppBar;
