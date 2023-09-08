import { useState } from "react";
import { getYear } from "date-fns";

export const useInputDatePicker = () => {
  const [month, setMonth] = useState(new Date().getMonth());

  const handleMonthChange = (date: Date) => {
    console.log(date);
    setMonth(date.getMonth());
  };

  const [selectedDate, setSelectedDate] = useState<Date | null>(null);

  const years = Array.from({ length: getYear(new Date()) + 1 - 1900 }, (_, i) => getYear(new Date()) - i);
  const months = ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"];

  return { selectedDate, setSelectedDate, month, handleMonthChange, years, months };
};
