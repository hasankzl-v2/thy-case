import { toast } from "react-toastify";
import IErrorResponse from "../types/response/IErrorResponse";

// spesific toast component for showing validation errors
const showErrorToast = ({ errorCode, errorMessage }: IErrorResponse) => {
  toast.error(
    <div>
      <strong>{errorCode}</strong>
      <p>{errorMessage}</p>
    </div>,
    {
      autoClose: 5000,
      hideProgressBar: false,
      closeOnClick: true,
      pauseOnHover: true,
      draggable: true,
    }
  );
};

const ToastComponents = {
  showErrorToast,
};

export default ToastComponents;
