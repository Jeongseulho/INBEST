interface TextInputProps {
  placeholder?: string;
}

const TextInput = ({ placeholder = "" }: TextInputProps) => {
  return <input type="text" placeholder={placeholder} className=" border border-solid border-gray-300 rounded-md" />;
};

export default TextInput;
