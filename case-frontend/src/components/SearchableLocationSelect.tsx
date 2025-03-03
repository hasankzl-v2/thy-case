import React, { useEffect, useState } from "react";
import { Autocomplete, TextField } from "@mui/material";
import ILocationData from "../types/ILocationData";
import LocationService from "../service/LocationService";
import { emptyLocation } from "../Constants";

interface SearchableSelectProps {
  handleSelect: (location: ILocationData | null) => void;
  selectedData: string;
  label: string;
}
const SearchableLocationSelect = ({
  handleSelect,
  selectedData,
  label,
}: SearchableSelectProps) => {
  const [data, setData] = useState<Array<ILocationData>>([]);
  const [search, setSearch] = useState<string>("");
  useEffect(() => {
    fetchData();
  }, [search]);
  const fetchData = () => {
    const location: ILocationData = {
      ...emptyLocation,
      locationCode: search,
    };
    LocationService.search(location, 0, 20).then((res) =>
      setData(res.data.content)
    );
  };
  const handleSearch = (s: string) => {
    setSearch(s);
  };
  return (
    <Autocomplete
      sx={{ margin: 4 }}
      options={data.map((loc) => loc.locationCode)}
      value={selectedData}
      onChange={(event, newValue) => {
        if (newValue) {
          handleSelect(data.filter((d) => d.locationCode == newValue)[0]);
        } else {
          handleSelect(null);
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

export default SearchableLocationSelect;
