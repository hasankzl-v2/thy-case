import { toast } from "react-toastify";
import IErrorResponse from "../types/response/IErrorResponse";
const showErrorToast = ({ errorCode, errorMessage }: IErrorResponse) => {
  toast.error(
    <div>
      <strong>{errorCode}</strong>
      <p>{errorMessage}</p>
    </div>,
    {
      autoClose: 5000, // 5 saniye sonra kapanır
      hideProgressBar: false, // İlerleme çubuğunu göster
      closeOnClick: true, // Tıklanınca kapanır
      pauseOnHover: true, // Üzerine gelindiğinde durur
      draggable: true, // Sürüklenebilir
    }
  );
};

const ToastComponents = {
  showErrorToast,
};

export default ToastComponents;
