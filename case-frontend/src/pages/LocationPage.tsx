import { useState, useEffect } from "react";
import ILocationData from "../types/ILocationData";
import LocationService from "../service/LocationService";
import Button from "@mui/material/Button";
import Paper from "@mui/material/Paper";
import { DataGrid, GridColDef } from "@mui/x-data-grid";
import { Edit as EditIcon, Delete as DeleteIcon } from "@mui/icons-material";
import { IconButton } from "@mui/material";
import ConfirmDialog from "../components/ConfirmationDialog";

import { toast } from "react-toastify";
import LocationModal from "../components/LocationModal";
function LocationPage() {
  const [data, setData] = useState<ILocationData[]>([]);
  const [openDialog, setOpenDialog] = useState(false); // Dialog açık mı kontrolü
  const [selectedData, setSelectedData] = useState<ILocationData | null>(); // Silinecek verinin id'si
  useEffect(() => {
    LocationService.getAll()
      .then((response) => setData(response.data))
      .catch(() => toast.error("Unexpected Error"))
      .finally(() => setLoading(false));
  }, []);

  const columns: GridColDef[] = [
    { field: "id", headerName: "ID", width: 130 },
    { field: "name", headerName: "Name", width: 470 },
    { field: "country", headerName: "Country", width: 330 },
    {
      field: "locationCode",
      headerName: "Location Code",
      width: 230,
    },
    {
      field: "Actions",
      headerName: "Actions",
      description: "Column Actions.",
      sortable: false,
      flex: 1,
      renderCell: (params) => {
        return (
          <div
            style={{
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            <IconButton onClick={() => handleEditClick(params.row)}>
              <EditIcon />
            </IconButton>
            <IconButton onClick={() => handleDeleteClick(params.row)}>
              <DeleteIcon />
            </IconButton>
          </div>
        );
      },
    },
  ];
  // Edit butonuna tıklama işlevi
  const handleEditClick = (row: ILocationData) => {
    toast("Wow so easy! " + row.city);
  };

  // Delete butonuna tıklama işlevi
  const handleDeleteClick = (row: ILocationData) => {
    setSelectedData(row);
    setOpenDialog(true);
  };

  const handleDeleteData = () => {
    if (selectedData != null) {
      LocationService.remove(selectedData.id).then(() => {
        toast("deleted successfuly");
        setOpenDialog(false);
        LocationService.getAll()
          .then((response) => {
            setData(response.data);
          })
          .catch(() => toast.error("Unexpected Error"));
      });
    }
  };

  const handleCancelDeleteData = () => {
    setSelectedData({}); // Silinecek verinin id'sini sakla
    setOpenDialog(false); // Dialog'u aç
  };

  const handleSave = () => {
    LocationService.getAll()
      .then((response) => {
        setData(response.data);
      })
      .catch(() => toast.error("Unexpected Error"));
  };
  const paginationModel = { page: 0, pageSize: 5 };
  return (
    <div>
      <Paper sx={{ mt: 5, height: 800, width: "100%" }}>
        <div
          style={{
            display: "flex",
            justifyContent: "right",
            alignItems: "right",
          }}
        >
          <LocationModal handleSave={handleSave} />
        </div>
        <DataGrid
          rows={data}
          columns={columns}
          initialState={{ pagination: { paginationModel } }}
          pageSizeOptions={[5, 10]}
          sx={{ border: 0 }}
        />
      </Paper>
      <ConfirmDialog
        message="Are you sure to delete this data?"
        isOpen={openDialog}
        handleCancel={handleCancelDeleteData}
        handleConfirm={handleDeleteData}
      />
    </div>
  );
}

export default LocationPage;
