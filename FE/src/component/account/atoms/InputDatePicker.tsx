import DatePicker from "tailwind-datepicker-react";
import { useInputDatePicker } from "./useInputDatePicker";
interface Props {
  value?: number | string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void;
}
const InputDatePicker = ({ onChange }: Props) => {
  const { options, show, handleChange, handleClose } = useInputDatePicker();
  return <DatePicker options={options} onChange={onChange} show={show} setShow={handleClose} />;
};

export default InputDatePicker;
