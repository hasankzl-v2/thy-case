import { useEffect, useState } from "react";
import { Autocomplete, TextField } from "@mui/material";
import ILocationData from "../types/ILocationData";
import LocationService from "../service/LocationService";
import { emptyLocation } from "../Constants";

interface SearchableSelectProps {
  handleSelect: (location: ILocationData | null) => void;
  selectedData: string;
  label: string;
}
// select component for location, it includes server side rendering by location code
const SearchableLocationSelect = ({
  handleSelect,
  selectedData,
  label,
}: SearchableSelectProps) => {
  const [data, setData] = useState<Array<ILocationData>>([]);
  const [search, setSearch] = useState<string>("");

  //when search updated fetch data again
  useEffect(() => {
    fetchData();
  }, [search]);

  // fetch data by location code
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
  // find selected value by given data . useful while updating Transportation
  const getValue = () => {
    const value = data.filter((d) => d.locationCode == selectedData)[0];
    if (value) {
      return value;
    } else {
      return null;
    }
  };
  return (
    <Autocomplete
      sx={{ maxWidth: 400 }}
      options={data}
      getOptionLabel={(option) =>
        `${option.locationCode} - ${option.city} - ${option.name}`
      }
      value={getValue()}
      onChange={(event, newValue) => {
        if (newValue) {
          handleSelect(newValue);
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
    />
  );
};

export default SearchableLocationSelect;
