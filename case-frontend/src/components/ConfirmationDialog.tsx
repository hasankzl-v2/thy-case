import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  Box,
  IconButton,
  Typography,
} from "@mui/material";
import { Close } from "@mui/icons-material";
interface ConfirmDialogProps {
  isOpen: boolean;
  handleCancel: () => void;
  handleConfirm: () => void;
  message: string;
}
const ConfirmDialog = ({
  isOpen,
  handleCancel,
  handleConfirm,
  message,
}: ConfirmDialogProps) => {
  return (
    <Dialog open={isOpen} maxWidth="sm" fullWidth>
      <DialogTitle>Confirm the action</DialogTitle>
      <Box position="absolute" top={0} right={0}>
        <IconButton>
          <Close />
        </IconButton>
      </Box>
      <DialogContent>
        <Typography>{message}</Typography>
      </DialogContent>
      <DialogActions>
        <Button color="primary" variant="contained" onClick={handleCancel}>
          Cancel
        </Button>
        <Button color="secondary" variant="contained" onClick={handleConfirm}>
          Confirm
        </Button>
      </DialogActions>
    </Dialog>
  );
};

export default ConfirmDialog;
