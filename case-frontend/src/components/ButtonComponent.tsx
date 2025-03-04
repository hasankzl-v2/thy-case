import React from "react";
import { Button } from "@mui/material";

interface ButtonComponentProps {
  text: String;
  onClick: () => void;
  icon?: React.ReactNode;
}
// button component style for thy application
const ButtonComponent = ({ text, onClick, icon }: ButtonComponentProps) => {
  return (
    <Button
      startIcon={icon}
      variant="contained"
      sx={{
        backgroundColor: "#e60012", // Türk Hava Yolları kırmızısı
        color: "white", // Beyaz metin
        "&:hover": {
          backgroundColor: "#c20010", // Hover durumunda daha koyu kırmızı
        },
      }}
      onClick={onClick}
    >
      {text}
    </Button>
  );
};

export default ButtonComponent;
