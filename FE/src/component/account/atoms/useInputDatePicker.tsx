import { useState } from "react";
export const useInputDatePicker = () => {
  const options = {
    title: "생년월일",
    autoHide: true,
    todayBtn: false,
    clearBtn: true,
    maxDate: new Date(),
    minDate: new Date("1950-01-01"),
    weekDays: ["월", "화", "수", "목", "금", "토", "일"],
    theme: {
      background: "",
      todayBtn: "",
      clearBtn: "",
      icons: "",
      text: "",
      disabledText: "text-gray-300",
      input: "",

      inputIcon: "",
      selected: "",
    },
    icons: {
      // () => ReactElement | JSX.Element
      prev: () => <span>←</span>,
      next: () => <span>→</span>,
    },
    datepickerClassNames: "top-12",
    defaultDate: new Date(),
    language: "ko",
  };

  const [show, setShow] = useState<boolean>(false);
  const handleChange = (selectedDate: Date) => {
    console.log(selectedDate);
  };
  const handleClose = (state: boolean) => {
    setShow(state);
  };

  return {
    options,
    show,
    setShow,
    handleChange,
    handleClose,
  };
};
