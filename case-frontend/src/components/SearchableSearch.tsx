import React, { useState } from "react";
import { Autocomplete, TextField } from "@mui/material";
import ILocationData from "../types/ILocationData";

interface SearchableSelectProps {
  handleSelect: (locationCode: string) => void;
  handleSearch: (search: string) => void;
  selectedData: string;
  data: Array<ILocationData>;
  label: string;
}
const SearchableSelect = ({
  data,
  handleSelect,
  handleSearch,
  selectedData,
  label,
}: SearchableSelectProps) => {
  return (
    <Autocomplete
      options={data.map((loc) => loc.locationCode)}
      value={selectedData}
      onChange={(event, newValue) => {
        if (newValue) {
          handleSelect(newValue);
        } else {
          handleSelect("");
        }
      }}
      renderInput={(params) => (
        <TextField
          {...params}
          label={label}
          variant="outlined"
          onChange={(e) => handleSearch(e.target.value)}
        />
      )}
      fullWidth
      sx={{ maxWidth: 400 }}
    />
  );
};

export default SearchableSelect;
